package com.example.movieproject.service;

import com.example.movieproject.common.type.Income;
import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.request.CinemaScheduleUpdateRequestDTO;
import com.example.movieproject.dto.response.*;
import com.example.movieproject.exceptionHandle.CinemaScheduleException;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.repository.ApplicationRepository;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CinemaScheduleService {

    private final CinemaScheduleRepository cinemaScheduleRepository;
    private final ApplicationRepository applicationRepository;
    private final MovieRepository movieRepository;
    private final MyCinemaRepository myCinemaRepository;

    @Transactional
    public CinemaScheduleCreateResponseDTO createCinemaSchedule(Long myCinemaId,Long movieId, CinemaScheduleCreateRequestDTO requestDTO){

        MyCinema myCinema = myCinemaRepository.findById(myCinemaId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_MY_CINEMA));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_MOVIE));

            List<CinemaSchedule> alreadyExistList = cinemaScheduleRepository.findByMyCinema(myCinema);

            isOverlap(alreadyExistList,requestDTO); // 해당 영화관의 시간대와 요청시간을 비교하는 로직

        CinemaSchedule cinemaSchedule=CinemaScheduleCreateRequestDTO.dtoToEntity(myCinema,movie,requestDTO);

        return CinemaScheduleCreateResponseDTO.entityToDTO(cinemaScheduleRepository.save(cinemaSchedule));
    }


    @Transactional(readOnly = true)
    public CinemaScheduleResponseDTO getCinemaSchedule(Long cinemaScheduleId){

        CinemaSchedule cinemaSchedule = cinemaScheduleRepository.findById(cinemaScheduleId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_CINEMA_SCHEDULE));

        return CinemaScheduleResponseDTO.entityToDTO(cinemaSchedule);
    }

    @Transactional(readOnly = true)
    public Map<String,List<CinemaScheduleListResponseDTO>> getCinemaScheduleList(Long myCinemaId){

        MyCinema myCinema = myCinemaRepository.findById(myCinemaId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_MY_CINEMA));

        Date now = new Date();
        now.getTime();

        return cinemaScheduleRepository.findSchedule(myCinema,now).stream()
                .map(CinemaScheduleListResponseDTO::entityToDTO)
                .collect(Collectors.groupingBy(CinemaScheduleListResponseDTO::getTitle));
    }

    @Transactional
    public Boolean updateCinemaSchedule(Long cinemaScheduleId, CinemaScheduleUpdateRequestDTO updateDTO){

        CinemaSchedule cinemaSchedule = cinemaScheduleRepository.findById(cinemaScheduleId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_CINEMA_SCHEDULE));

        cinemaSchedule.updateCinemaSchedule(updateDTO);

        return true;
    }

    @Transactional
    public Boolean deleteCinemaSchedule(Long cinemaScheduleId){

        // 예약 엔티티 없어야 함
        CinemaSchedule cinemaSchedule = cinemaScheduleRepository.findById(cinemaScheduleId)
                .orElseThrow(()-> new CinemaScheduleException(ErrorList.NOT_EXIST_CINEMA_SCHEDULE));

        applicationRepository.deleteAllByCinemaSchedule(cinemaSchedule);

        cinemaScheduleRepository.deleteById(cinemaScheduleId);

        return true;
    }

    @Transactional(readOnly = true)
    public List<YearIncomeResponseDTO> getYearIncome(Long myCinemaId, Income income){

        List<YearIncomeResponseDTO> dtoList = new ArrayList<>();

        if(income==Income.YEAR){
            List<Object[]> resultList = cinemaScheduleRepository.getYearIncome(myCinemaId);

            for(Object[] result:resultList){

                int year = Integer.parseInt(String.valueOf(result[0]));
                int totalIncome = Integer.parseInt(String.valueOf(result[1]));


                YearIncomeResponseDTO dto = YearIncomeResponseDTO.YearIncome(year,totalIncome);
                dtoList.add(dto);
            }
        } else if (income==Income.YEAR_MONTH) {
            List<Object[]> resultList = cinemaScheduleRepository.getYearAndMonth(myCinemaId);

            for(Object[] result:resultList){

                int year = Integer.parseInt(String.valueOf(result[0]));
                int month = Integer.parseInt(String.valueOf(result[1]));
                int totalIncome = Integer.parseInt(String.valueOf(result[2]));

                YearIncomeResponseDTO dto = YearIncomeResponseDTO.YearAndMonthIncome(year,month,totalIncome);
                dtoList.add(dto);
            }
        }else{
            List<Object[]> resultList = cinemaScheduleRepository.getYearAndMonthAndWeek(myCinemaId);

            for(Object[] result:resultList){

                int year = Integer.parseInt(String.valueOf(result[0]));
                int month = Integer.parseInt(String.valueOf(result[1]));
                int week = Integer.parseInt(String.valueOf(result[2]));
                int totalIncome = Integer.parseInt(String.valueOf(result[3]));

                YearIncomeResponseDTO dto = YearIncomeResponseDTO.YearAndMonthAndWeekIncome(year,month,week,totalIncome);;
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private void isOverlap(List<CinemaSchedule> existList, CinemaScheduleCreateRequestDTO requestDTO){
         for(CinemaSchedule exist: existList){
             if(exist.getShowDate().before(requestDTO.getEndDate()) && exist.getEndDate().after(requestDTO.getShowDate())) {
                 throw new CinemaScheduleException(ErrorList.ALREADY_EXIST_SCHEDULE);
             }
         }
    }
}

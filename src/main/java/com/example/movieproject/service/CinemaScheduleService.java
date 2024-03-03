package com.example.movieproject.service;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.request.CinemaScheduleUpdateRequestDTO;
import com.example.movieproject.dto.response.CinemaResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleListResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleResponseDTO;
import com.example.movieproject.exceptionHandle.CinemaScheduleException;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaScheduleService {

    private final CinemaScheduleRepository cinemaScheduleRepository;
    private final MovieRepository movieRepository;
    private final MyCinemaRepository myCinemaRepository;

    @Transactional
    public CinemaScheduleCreateResponseDTO createCinemaSchedule(Long myCinemaId,Long movieId, CinemaScheduleCreateRequestDTO requestDTO){

        MyCinema myCinema = myCinemaRepository.findById(myCinemaId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_MY_CINEMA));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()->new CinemaScheduleException(ErrorList.NOT_EXIST_MOVIE));

        CinemaSchedule cinemaSchedule = CinemaScheduleCreateRequestDTO.dtoToEntity(myCinema,movie,requestDTO);

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

        cinemaScheduleRepository.deleteById(cinemaScheduleId);

        return true;
    }











}

package com.example.movieproject.service;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.exceptionHandle.CinemaScheduleException;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.MyCinemaException;
import com.example.movieproject.repository.ApplicationRepository;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CinemaScheduleServiceTest {

    @InjectMocks
    private CinemaScheduleService cinemaScheduleService;

    @Mock
    private CinemaScheduleRepository cinemaScheduleRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MyCinemaRepository myCinemaRepository;

    @DisplayName("[영화관-스케줄]-생성 성공")
    @Test
    void createSuccess(){

        //given

        CinemaScheduleCreateRequestDTO requestDTO = CinemaScheduleCreateRequestDTO.builder().build();

        MyCinema myCinema = MyCinema.builder()
                .myCinemaId(1L)
                .build();
        Movie movie = Movie.builder()
                .MovieId(1L)
                .build();

        CinemaSchedule cinemaSchedule = CinemaSchedule.builder()
                .cinemaScheduleId(1L)
                .myCinema(myCinema)
                .movie(movie)
                .price(2000)
                .build();


        given(myCinemaRepository.findById(any())).willReturn(Optional.of(myCinema));
        given(movieRepository.findById(any())).willReturn(Optional.of(movie));
        given(cinemaScheduleRepository.save(any())).willReturn(cinemaSchedule);


        //when
        CinemaScheduleCreateResponseDTO responseDTO = cinemaScheduleService.createCinemaSchedule(1L,1L,requestDTO);

        //then
        assertEquals(2000,responseDTO.getPrice());
    }

    @DisplayName("[영화관 생성]-영화관 존재 안함")
    @Test
    void failNotExistCinema(){

        //given
        given(myCinemaRepository.findById(any())).willReturn(Optional.empty());

        //when
        CinemaScheduleException cinemaScheduleException = assertThrows(CinemaScheduleException.class,
                ()->cinemaScheduleService.createCinemaSchedule(1L,1L,null));

        //then
        assertEquals(cinemaScheduleException.getErrorMessage(), ErrorList.NOT_EXIST_MY_CINEMA.getErrorMessage());
    }




}

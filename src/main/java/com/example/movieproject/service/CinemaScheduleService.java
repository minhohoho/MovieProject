package com.example.movieproject.service;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CinemaScheduleService {

    private final CinemaScheduleRepository cinemaScheduleRepository;
    private final MovieRepository movieRepository;
    private final MyCinemaRepository myCinemaRepository;

    @Transactional
    public CinemaScheduleCreateResponseDTO createSchedule(Long myCinemaId, Long movieId,CinemaScheduleCreateRequestDTO requestDTO){

        MyCinema myCinema = myCinemaRepository.findById(myCinemaId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();

        CinemaSchedule cinemaSchedule = CinemaScheduleCreateRequestDTO.dtoToEntity(myCinema,movie,requestDTO);

        return CinemaScheduleCreateResponseDTO.entityToDTO(cinemaScheduleRepository.save(cinemaSchedule));
    }



}

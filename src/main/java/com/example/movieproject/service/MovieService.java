package com.example.movieproject.service;


import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Movie_Staff;
import com.example.movieproject.domain.Staff;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.request.MovieStaffCreateRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.Movie_StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final Movie_StaffRepository movieStaffRepository;

    @Transactional
    public MovieCreateResponseDTO createMovie(MovieCreateRequestDTO requestDTO){

        Movie movie = MovieCreateRequestDTO.dtoToEntity(requestDTO);

        List<Movie_Staff> movieStaffList= requestDTO.getMovieStaffListDTO().stream().map(dto->MovieStaffCreateRequestDTO.dtoToEntity(movie,dto)).toList();

        movieRepository.save(movie);
        movieStaffRepository.saveAll(movieStaffList);

        return MovieCreateResponseDTO.EntityToDTO(movie);
    }










}

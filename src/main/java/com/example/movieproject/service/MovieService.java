package com.example.movieproject.service;


import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MovieStaff;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.request.MovieStaffCreateRequestDTO;
import com.example.movieproject.dto.request.MovieUpdateRequestDTO;
import com.example.movieproject.dto.request.SearchRequestDTO;
import com.example.movieproject.dto.response.*;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.MovieException;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MovieStaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.movieproject.exceptionHandle.ErrorList.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieStaffRepository movieStaffRepository;


    @Transactional
    public MovieCreateResponseDTO createMovie(MovieCreateRequestDTO requestDTO){

        Movie movie = MovieCreateRequestDTO.dtoToEntity(requestDTO);

        List<MovieStaff> movieStaffList= requestDTO.getMovieStaffListDTO().stream().map(dto->MovieStaffCreateRequestDTO.dtoToEntity(movie,dto)).toList();

        movieRepository.save(movie);
        movieStaffRepository.saveAll(movieStaffList);

        return MovieCreateResponseDTO.EntityToDTO(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieListResponseDTO> searchMovieList(Pageable pageable, SearchRequestDTO searchRequestDTO){

        if(searchRequestDTO==null){
            return movieRepository.findAll(pageable).map(MovieListResponseDTO::EntityToDTO);
        }
        return movieRepository.searchMovieList(pageable,searchRequestDTO);
    }

    @Transactional(readOnly = true)
    public MovieWithScoreResponseDTO getMovie(Long movieId,double averageScore){

        movieRepository.findById(movieId).orElseThrow(()->new MovieException(NOT_EXIST_MOVIE));

        MovieStaffResponseDTO movieStaffResponseDTO = movieStaffRepository.findMovieAndStaffInfo(movieId);

        averageScore=Math.round(averageScore*10)/10.0;

        return MovieWithScoreResponseDTO.builder()
                .movieStaffResponseDTO(movieStaffResponseDTO)
                .averageScore(averageScore)
                .build();
    }

    @Transactional
    public MovieUpdateResponseDTO updateMovie(Long movieId, MovieUpdateRequestDTO updateDTO){

        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieException(NOT_EXIST_MOVIE));

        movie.updateMovie(updateDTO);

        return MovieUpdateResponseDTO.EntityToDTO(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId){

        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieException(NOT_EXIST_MOVIE));

        movieStaffRepository.deleteByMovie(movie);

        movieRepository.deleteById(movie.getMovieId());
    }












}

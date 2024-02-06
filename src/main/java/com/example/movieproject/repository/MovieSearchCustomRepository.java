package com.example.movieproject.repository;

import com.example.movieproject.dto.request.SearchRequestDTO;
import com.example.movieproject.dto.response.MovieListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieSearchCustomRepository{

    Page<MovieListResponseDTO> searchMovieList(Pageable pageable, SearchRequestDTO searchRequestDTO);


}

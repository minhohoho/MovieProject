package com.example.movieproject.repository;

import com.example.movieproject.dto.response.MovieStaffResponseDTO;

public interface MovieStaffCustomRepository {

    MovieStaffResponseDTO findMovieAndStaffInfo(Long movieId);

}

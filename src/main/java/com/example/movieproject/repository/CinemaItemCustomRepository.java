package com.example.movieproject.repository;

import com.example.movieproject.dto.response.MyCinemaResponseDTO;

public interface CinemaItemCustomRepository {

    MyCinemaResponseDTO getMyCinemaWithItemList(Long myCinemaId);

}

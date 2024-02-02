package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.domain.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieResponseDTO {

    private Long movieId;

    private String title;

    private Double score;

    private Age age;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date openingDate;

    private String duringTime;

    private MovieTheme movieTheme;



    public static MovieResponseDTO EntityToDTO(Movie movie){
        return MovieResponseDTO.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .score(3.6)
                .age(movie.getAge())
                .openingDate(movie.getOpeningDate())
                .duringTime(movie.getDuringTime())
                .movieTheme(movie.getMovieTheme())
                .build();
    }



}

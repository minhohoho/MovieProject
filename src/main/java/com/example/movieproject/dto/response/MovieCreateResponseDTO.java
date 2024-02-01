package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieCreateResponseDTO {

    private Long movieId;
    private String title;
    private Age age;
    private Date openingDate;
    private String duringTime;
    private MovieTheme movieTheme;
    private String content;



    public static MovieCreateResponseDTO EntityToDTO(Movie movie){
        return MovieCreateResponseDTO.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .age(movie.getAge())
                .openingDate(movie.getOpeningDate())
                .duringTime(movie.getDuringTime())
                .movieTheme(movie.getMovieTheme())
                .content(movie.getContent())
                .build();
    }



}

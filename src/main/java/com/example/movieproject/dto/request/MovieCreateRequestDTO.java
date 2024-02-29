package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.domain.Movie;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieCreateRequestDTO {


    private String title;

    private Age age;

    private Date openingDate;

    private String duringTime;

    private MovieTheme movieTheme;

    private String content;

    private List<MovieStaffCreateRequestDTO> movieStaffListDTO;

    public static Movie dtoToEntity(MovieCreateRequestDTO requestDTO){
        return Movie.builder()
                .title(requestDTO.getTitle())
                .age(requestDTO.getAge())
                .openingDate(requestDTO.getOpeningDate())
                .duringTime(requestDTO.getDuringTime())
                .movieTheme(requestDTO.getMovieTheme())
                .content(requestDTO.getContent())
                .build();
    }



}

package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Movie_Staff;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.security.cert.CertPathBuilder;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieCreateRequestDTO {

    @ApiParam(value="영화 제목")
    private String title;
    @ApiParam(value="상영 관람 나이")
    private Age age;
    @ApiParam(value="개봉일자")
    private Date openingDate;
    @ApiParam(value="상영시간")
    private String duringTime;
    @ApiParam(value="영화 테마")
    private MovieTheme movieTheme;
    @ApiParam(value="영화 내용")
    private String content;
    @ApiParam(value="영화 소속 배우 및 감독")
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

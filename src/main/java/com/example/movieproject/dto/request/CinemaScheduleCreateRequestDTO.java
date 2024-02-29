package com.example.movieproject.dto.request;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleCreateRequestDTO {

    private Date showDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;

    public static CinemaSchedule dtoToEntity(MyCinema myCinema, Movie movie, CinemaScheduleCreateRequestDTO requestDTO){

        return CinemaSchedule.builder()
                .myCinema(myCinema)
                .movie(movie)
                .showDate(requestDTO.getShowDate())
                .price(requestDTO.getPrice())
                .headCount(requestDTO.getHeadCount())
                .headCount(requestDTO.getHeadCount())
                .limitCount(requestDTO.getLimitCount())
                .build();
    }



}

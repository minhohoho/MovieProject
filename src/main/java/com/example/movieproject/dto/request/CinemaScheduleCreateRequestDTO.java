package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.CinemaScheduleStatus;
import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MyCinema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleCreateRequestDTO {


    private Date showDate;
    private Date endDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;

    public static CinemaSchedule dtoToEntity(MyCinema myCinema, Movie movie, CinemaScheduleCreateRequestDTO requestDTO){

        return CinemaSchedule.builder()
                .myCinema(myCinema)
                .movie(movie)
                .showDate(requestDTO.getShowDate())
                .endDate(requestDTO.getEndDate())
                .price(requestDTO.getPrice())
                .headCount(requestDTO.getHeadCount())
                .limitCount(requestDTO.getLimitCount())
                .cinemaScheduleStatus(CinemaScheduleStatus.OPEN)
                .build();
    }



}

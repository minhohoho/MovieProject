package com.example.movieproject.dto.response;

import com.example.movieproject.domain.CinemaSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleListResponseDTO {

    private Long cinemaScheduleId;
    private String title;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private Date showDate;
    private Integer headCount;
    private Integer limitCount;

    public static CinemaScheduleListResponseDTO entityToDTO(CinemaSchedule cinemaSchedule){
        return CinemaScheduleListResponseDTO.builder()
                .cinemaScheduleId(cinemaSchedule.getCinemaScheduleId())
                .title(cinemaSchedule.getMovie().getTitle())
                .showDate(cinemaSchedule.getShowDate())
                .headCount(cinemaSchedule.getHeadCount())
                .limitCount(cinemaSchedule.getLimitCount())
                .build();
    }




}

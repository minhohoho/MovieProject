package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.CinemaScheduleStatus;
import com.example.movieproject.domain.CinemaSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleResponseDTO {

    private Long cinemaScheduleId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private Date showDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;
    private CinemaScheduleStatus cinemaScheduleStatus;

    public static CinemaScheduleResponseDTO entityToDTO(CinemaSchedule cinemaSchedule){
        return CinemaScheduleResponseDTO.builder()
                .cinemaScheduleId(cinemaSchedule.getCinemaScheduleId())
                .showDate(cinemaSchedule.getShowDate())
                .price(cinemaSchedule.getPrice())
                .headCount(cinemaSchedule.getHeadCount())
                .limitCount(cinemaSchedule.getLimitCount())
                .cinemaScheduleStatus(cinemaSchedule.getCinemaScheduleStatus())
                .build();
    }


}

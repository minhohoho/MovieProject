package com.example.movieproject.dto.response;

import com.example.movieproject.domain.CinemaSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleCreateResponseDTO {

    private Long cinemaScheduleId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private Date showDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private Date endDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;

    public static CinemaScheduleCreateResponseDTO entityToDTO(CinemaSchedule cinemaSchedule){
        return CinemaScheduleCreateResponseDTO.builder()
                .cinemaScheduleId(cinemaSchedule.getCinemaScheduleId())
                .showDate(cinemaSchedule.getShowDate())
                .endDate(cinemaSchedule.getEndDate())
                .price(cinemaSchedule.getPrice())
                .headCount(cinemaSchedule.getHeadCount())
                .limitCount(cinemaSchedule.getLimitCount())
                .build();
    }


}

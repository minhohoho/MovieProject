package com.example.movieproject.dto.response;

import com.example.movieproject.domain.CinemaSchedule;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleCreateResponseDTO {

    private Long cinemaScheduleId;
    private Date showDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;

    public static CinemaScheduleCreateResponseDTO entityToDTO(CinemaSchedule cinemaSchedule){
        return CinemaScheduleCreateResponseDTO.builder()
                .cinemaScheduleId(cinemaSchedule.getCinemaScheduleId())
                .showDate(cinemaSchedule.getShowDate())
                .price(cinemaSchedule.getPrice())
                .headCount(cinemaSchedule.getHeadCount())
                .limitCount(cinemaSchedule.getLimitCount())
                .build();
    }


}

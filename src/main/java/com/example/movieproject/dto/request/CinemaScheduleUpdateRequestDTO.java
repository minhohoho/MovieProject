package com.example.movieproject.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaScheduleUpdateRequestDTO {

    private Date showDate;
    private Integer price;
    private Integer headCount;
    private Integer limitCount;
}

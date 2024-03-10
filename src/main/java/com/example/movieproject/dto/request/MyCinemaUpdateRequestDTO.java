package com.example.movieproject.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyCinemaUpdateRequestDTO {

    private String cinemaName;

    private String cinemaDetail;

    private String addressName;



}

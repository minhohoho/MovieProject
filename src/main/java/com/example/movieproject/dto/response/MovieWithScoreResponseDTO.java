package com.example.movieproject.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieWithScoreResponseDTO {

    private MovieStaffResponseDTO movieStaffResponseDTO;
    private double averageScore;

}

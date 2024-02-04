package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieStaffResponseDTO {

    private Long movieId;
    private String title;
    private Age age;
    private String duringTime;
    private MovieTheme movieTheme;
    private List<StaffResponseDTO> staffResponseDTOList;
}

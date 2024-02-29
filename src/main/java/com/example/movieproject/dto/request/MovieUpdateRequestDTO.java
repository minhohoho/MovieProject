package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieUpdateRequestDTO {

    private String title;

    private Age age;

    private Date openingDate;

    private String duringTime;

    private MovieTheme movieTheme;

    private String content;


}

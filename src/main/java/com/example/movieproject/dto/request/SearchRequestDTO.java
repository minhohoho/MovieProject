package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.MovieTheme;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SearchRequestDTO {
    private String title;
    private Date openingDate;
    private MovieTheme movieTheme;
}

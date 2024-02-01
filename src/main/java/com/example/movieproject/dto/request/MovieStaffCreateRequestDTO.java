package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Movie_Staff;
import com.example.movieproject.domain.Staff;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieStaffCreateRequestDTO {

    private Long staffId;
    private Role role;

    public static Movie_Staff dtoToEntity(Movie movie,MovieStaffCreateRequestDTO dto){
        return Movie_Staff.builder()
                .movie(movie)
                .staff(Staff.builder()
                        .StaffId(dto.staffId)
                        .build())
                .role(dto.getRole())
                .build();
    }



}

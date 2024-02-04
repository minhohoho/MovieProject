package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MovieStaff;
import com.example.movieproject.domain.Staff;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MovieStaffCreateRequestDTO {

    private Long staffId;
    private Role role;

    public static MovieStaff dtoToEntity(Movie movie, MovieStaffCreateRequestDTO dto){
        return MovieStaff.builder()
                .movie(movie)
                .staff(Staff.builder()
                        .StaffId(dto.staffId)
                        .build())
                .role(dto.getRole())
                .build();
    }



}

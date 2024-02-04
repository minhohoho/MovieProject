package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StaffResponseDTO {

    private String name;
    private Role role;
}

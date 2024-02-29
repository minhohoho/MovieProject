package com.example.movieproject.dto.request;

import lombok.*;

import java.util.Date;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StaffUpdateRequestDTO {

    private String name;

    private Date birth;

    private String nation;

}

package com.example.movieproject.dto.response;

import com.example.movieproject.domain.Staff;
import lombok.*;

import java.util.Date;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StaffUpdateResponseDTO {

    private Long staffId;

    private String name;

    private Date birth;

    private String nation;

    public static StaffUpdateResponseDTO entityToDTO(Staff staff){
        return StaffUpdateResponseDTO.builder()
                .staffId(staff.getStaffId())
                .name(staff.getName())
                .birth(staff.getBirth())
                .nation(staff.getName())
                .build();
    }

}

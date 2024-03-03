package com.example.movieproject.dto.response;

import com.example.movieproject.domain.Application;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyApplicationListResponseDTO {

    private Long applicationId;
    private Integer price;
    private LocalDateTime createdAt;

    public static MyApplicationListResponseDTO entityToDTO(Application application){
        return MyApplicationListResponseDTO.builder()
                .applicationId(application.getApplicationId())
                .price(application.getCinemaSchedule().getPrice())
                .createdAt(application.getCreatedAt())
                .build();
    }



}

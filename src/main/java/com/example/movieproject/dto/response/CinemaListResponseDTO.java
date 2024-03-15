package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaListResponseDTO {

    private Long myCinemaId;
    private String cinemaName;
    private LocalDateTime createdAt;
    private String myCinemaImageUrl;

    public static CinemaListResponseDTO entityToDTO(MyCinema myCinema){
        return CinemaListResponseDTO.builder()
                .myCinemaId(myCinema.getMyCinemaId())
                .cinemaName(myCinema.getCinemaName())
                .createdAt(myCinema.getCreatedAt())
                .myCinemaImageUrl(myCinema.getMyCinemaImageUrl())
                .build();
    }

}

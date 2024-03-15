package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyCinemaListResponseDTO {
    private Long myCinemaId;
    private String cinemaName;
    private String myCinemaImageUrl;

    private String addressName;
    private double distance;

    public static MyCinemaListResponseDTO entityToDTO(MyCinema myCinema){
        return MyCinemaListResponseDTO.builder()
                .myCinemaId(myCinema.getMyCinemaId())
                .cinemaName(myCinema.getCinemaName())
                .myCinemaImageUrl(myCinema.getMyCinemaImageUrl())
                .build();
    }




}

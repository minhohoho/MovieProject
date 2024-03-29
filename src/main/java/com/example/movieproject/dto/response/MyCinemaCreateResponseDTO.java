package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyCinemaCreateResponseDTO {

    private Long myCinemaId;

    private String cinemaName;

    private String cinemaDetail;

    private String addressName;

    private double latitude;

    private double longitude;

    public static MyCinemaCreateResponseDTO entityToDTO(MyCinema myCinema){
        return MyCinemaCreateResponseDTO.builder()
                .myCinemaId(myCinema.getMyCinemaId())
                .cinemaName(myCinema.getCinemaName())
                .cinemaDetail(myCinema.getCinemaDetail())
                .addressName(myCinema.getAddressName())
                .latitude(myCinema.getLatitude())
                .longitude(myCinema.getLongitude())
                .build();
    }



}

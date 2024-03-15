package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyCinemaResponseDTO {

    private Long myCinemaId;
    private String cinemaName;
    private String cinemaDetail;
    private String addressName;
    private double latitude;
    private double longitude;
    private String myCinemaImageUrl;

    public static MyCinemaResponseDTO entityToDTO(MyCinema myCinema){
        return MyCinemaResponseDTO.builder()
                .myCinemaId(myCinema.getMyCinemaId())
                .cinemaName(myCinema.getCinemaName())
                .cinemaDetail(myCinema.getCinemaDetail())
                .addressName(myCinema.getAddressName())
                .latitude(myCinema.getLatitude())
                .longitude(myCinema.getLongitude())
                .myCinemaImageUrl(myCinema.getMyCinemaImageUrl())
                .build();
    }

}

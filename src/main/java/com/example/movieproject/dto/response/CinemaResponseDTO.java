package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaResponseDTO {

    private Long myCinemaId;
    private String name;
    private String cinemaName;
    private String cinemaDetail;
    private String addressName;
    private double latitude;
    private double longitude;
    private String myCinemaImageUrl;

    public static CinemaResponseDTO entityToDTO(MyCinema myCinema,String name){
        return CinemaResponseDTO.builder()
                .myCinemaId(myCinema.getMyCinemaId())
                .name(name)
                .cinemaName(myCinema.getCinemaName())
                .cinemaDetail(myCinema.getCinemaDetail())
                .addressName(myCinema.getAddressName())
                .latitude(myCinema.getLatitude())
                .longitude(myCinema.getLongitude())
                .myCinemaImageUrl(myCinema.getMyCinemaImageUrl())
                .build();
    }


}

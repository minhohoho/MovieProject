package com.example.movieproject.dto.response;

import com.example.movieproject.domain.MyCinema;
import lombok.*;

import java.util.List;

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
    private List<CinemaItemResponseDTO> cinemaItemResponseDTOList;


}

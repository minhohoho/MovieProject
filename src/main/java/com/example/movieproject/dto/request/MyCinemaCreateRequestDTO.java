package com.example.movieproject.dto.request;


import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyCinemaCreateRequestDTO {

    private String cinemaName;

    private String cinemaDetail;

    private String cinemaItem;

    private String addressName;

    public static MyCinema dtoToEntity(Member member,MyCinemaCreateRequestDTO requestDTO, KakakoApiResponseDTO kakakoApiResponseDTO){
      return
              MyCinema.builder()
                      .member(member)
                      .cinemaName(requestDTO.getCinemaName())
                      .cinemaDetail(requestDTO.getCinemaDetail())
                      .cinemaItem(requestDTO.getCinemaItem())
                      .addressName(kakakoApiResponseDTO.getDocumentList().get(0).getAddressName())
                      .latitude(kakakoApiResponseDTO.getDocumentList().get(0).getLatitude())
                      .longitude(kakakoApiResponseDTO.getDocumentList().get(0).getLongitude())
                      .build();
    }


}

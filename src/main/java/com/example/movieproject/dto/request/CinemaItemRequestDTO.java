package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.SellType;
import com.example.movieproject.domain.CinemaItem;
import com.example.movieproject.domain.MyCinema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaItemRequestDTO {

    private String itemName;
    private String itemDetail;
    private Integer price;

    public static CinemaItem dtoTonEntity(CinemaItemRequestDTO requestDTO, MyCinema myCinema){
        return CinemaItem.builder()
                .myCinema(myCinema)
                .itemName(requestDTO.getItemName())
                .itemDetail(requestDTO.getItemDetail())
                .price(requestDTO.getPrice())
                .sellType(SellType.SELL)
                .build();
    }


}

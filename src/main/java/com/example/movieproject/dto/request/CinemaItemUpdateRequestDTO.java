package com.example.movieproject.dto.request;

import com.example.movieproject.common.type.SellType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaItemUpdateRequestDTO {

    private String itemName;
    private String itemDetail;
    private Integer price;
    private SellType sellType;

}

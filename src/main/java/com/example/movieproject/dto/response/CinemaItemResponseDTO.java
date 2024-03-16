package com.example.movieproject.dto.response;

import com.example.movieproject.common.type.SellType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CinemaItemResponseDTO {

    private Long cinemaItemId;
    private String itemName;
    private String itemDetail;
    private Integer price;
    private SellType sellType;
    private String CinemaItemImageUrl;
}

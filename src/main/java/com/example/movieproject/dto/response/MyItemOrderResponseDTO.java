package com.example.movieproject.dto.response;

import com.example.movieproject.domain.ItemOrder;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyItemOrderResponseDTO {

    private Long itemOrderId;

    private String itemName;

    private int cnt;

    private int ItemTotalPrice;

    public static MyItemOrderResponseDTO entityToDTO(ItemOrder itemOrder,String itemName){

        return MyItemOrderResponseDTO.builder()
                .itemOrderId(itemOrder.getItemOrderId())
                .itemName(itemName)
                .cnt(itemOrder.getCnt())
                .ItemTotalPrice(itemOrder.getItemTotalPrice())
                .build();
    }

}

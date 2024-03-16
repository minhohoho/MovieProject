package com.example.movieproject.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ItemOrderCreateRequestDTO {

    private Long cinemaItemId;
    private int cnt;

}

package com.example.movieproject.dto.response;

import lombok.*;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyReviewListResponseDTO {

    private int count;
    private List<ReviewResponse> reviewResponseList;

}

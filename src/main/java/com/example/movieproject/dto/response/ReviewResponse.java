package com.example.movieproject.dto.response;

import com.example.movieproject.domain.Review;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long reviewId;
    private String name;
    private String content;
    private double score;
    private int reviewCnt;

    public static ReviewResponse EntityToDTO(Review review){
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .name(review.getMember().getName())
                .content(review.getContent())
                .score(review.getScore())
                .reviewCnt(review.getReviewCnt())
                .build();
    }



}

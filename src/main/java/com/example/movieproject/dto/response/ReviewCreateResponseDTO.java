package com.example.movieproject.dto.response;

import com.example.movieproject.domain.Review;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewCreateResponseDTO {

    private Long ReviewId;

    public static ReviewCreateResponseDTO EntityToDTO(Review review){
        return ReviewCreateResponseDTO.builder()
                .ReviewId(review.getReviewId())
                .build();
    }

}

package com.example.movieproject.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class ReviewLikeResponse {
    private boolean result;
    private String message;
}

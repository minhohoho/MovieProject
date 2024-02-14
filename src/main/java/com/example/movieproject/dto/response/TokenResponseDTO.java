package com.example.movieproject.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}

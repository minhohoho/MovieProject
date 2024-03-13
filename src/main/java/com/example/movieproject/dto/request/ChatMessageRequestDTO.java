package com.example.movieproject.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessageRequestDTO {

    private Long memberId;
    private String message;

}

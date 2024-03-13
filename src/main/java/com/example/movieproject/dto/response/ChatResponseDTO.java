package com.example.movieproject.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatResponseDTO {

    private String sender;
    private String message;
    private LocalDateTime time;

}

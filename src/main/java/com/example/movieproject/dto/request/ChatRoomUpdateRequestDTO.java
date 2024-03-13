package com.example.movieproject.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoomUpdateRequestDTO {

    private String chatRoomTitle;
    private String chatRoomExplain;

}

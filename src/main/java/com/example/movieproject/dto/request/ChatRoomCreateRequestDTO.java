package com.example.movieproject.dto.request;

import com.example.movieproject.domain.ChatRoom;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoomCreateRequestDTO {

    private String chatRoomTitle;
    private String chatRoomExplain;

    public static ChatRoom dtoToEntity(ChatRoomCreateRequestDTO requestDTO){
        return ChatRoom.builder()
                .member(null)
                .chatRoomTitle(requestDTO.getChatRoomTitle())
                .chatRoomExplain(requestDTO.getChatRoomExplain())
                .build();
    }




}

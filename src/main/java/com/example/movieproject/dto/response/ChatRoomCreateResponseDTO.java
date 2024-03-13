package com.example.movieproject.dto.response;

import com.example.movieproject.domain.ChatRoom;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoomCreateResponseDTO {

    private Long chatRoomId;
    private String chatRoomTitle;

    public static ChatRoomCreateResponseDTO entityToDTO(ChatRoom chatRoom){
        return ChatRoomCreateResponseDTO.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomTitle(chatRoom.getChatRoomTitle())
                .build();
    }


}

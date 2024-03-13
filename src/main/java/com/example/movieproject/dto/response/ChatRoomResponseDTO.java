package com.example.movieproject.dto.response;

import com.example.movieproject.domain.ChatRoom;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoomResponseDTO {

    private Long chatRoomId;
    private String memberName;
    private String chatRoomTitle;
    private String chatRoomExplain;

    public static ChatRoomResponseDTO entityToDTO(ChatRoom chatRoom){
        return  ChatRoomResponseDTO.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .memberName(chatRoom.getMember().getName())
                .chatRoomTitle(chatRoom.getChatRoomTitle())
                .chatRoomExplain(chatRoom.getChatRoomExplain())
                .build();
    }



}

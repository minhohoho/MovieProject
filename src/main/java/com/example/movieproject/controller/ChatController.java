package com.example.movieproject.controller;


import com.example.movieproject.dto.request.ChatMessageRequestDTO;
import com.example.movieproject.dto.response.ChatResponseDTO;
import com.example.movieproject.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;


    @MessageMapping("chat.enter.{chatRoomId}")
    public ChatResponseDTO enterUser(@DestinationVariable Long chatRoomId,@Payload ChatMessageRequestDTO requestDTO){


        return chatService.enterUser(chatRoomId,requestDTO.getMemberId());
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public ChatResponseDTO sendMessage(@DestinationVariable Long chatRoomId, @Payload ChatMessageRequestDTO requestDTO){

       return chatService.sendMessage(chatRoomId,requestDTO);
    }

    @MessageMapping("chat.exit.{chatRoomId}")
    public ChatResponseDTO exitUser(@DestinationVariable Long chatRoomId,@Payload ChatMessageRequestDTO requestDTO){

        return chatService.exitUser(chatRoomId, requestDTO.getMemberId());
    }



}

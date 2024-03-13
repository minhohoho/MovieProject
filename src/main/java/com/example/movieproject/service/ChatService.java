package com.example.movieproject.service;

import com.example.movieproject.domain.ChatMessage;
import com.example.movieproject.domain.ChatRoom;
import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Review;
import com.example.movieproject.dto.request.ChatMessageRequestDTO;
import com.example.movieproject.dto.response.ChatResponseDTO;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.ChatMessageRepository;
import com.example.movieproject.repository.ChatRoomRepository;
import com.example.movieproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final RabbitTemplate rabbitTemplate;

    public ChatResponseDTO enterUser(Long chatRoomId,Long memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Member member = memberRepository.findById(memberId).orElseThrow();

        String message = member.getName() + "님이 채팅에 참여하셨습니다";

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME,"room."+chatRoomId,message);

        return ChatResponseDTO.builder()
                .sender(member.getName())
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }

    public ChatResponseDTO sendMessage(Long chatRoomId, ChatMessageRequestDTO requestDTO){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Member member = memberRepository.findById(requestDTO.getMemberId()).orElseThrow();

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME,"room."+chatRoomId,requestDTO.getMessage());

        ChatMessage chatMessage = ChatMessage.builder()
                .member(member)
                .chatRoom(chatRoom)
                .message(requestDTO.getMessage())
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatResponseDTO.builder()
                .sender(member.getName())
                .message(requestDTO.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

    public ChatResponseDTO exitUser(Long chatRoomId,Long memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Member member = memberRepository.findById(memberId).orElseThrow();

        String message = member.getName() + "님이 채팅방에서 퇴장하셨습니다";

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME,"room."+chatRoomId,message);

        return ChatResponseDTO.builder()
                .sender(member.getName())
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }




}

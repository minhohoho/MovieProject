package com.example.movieproject.service;

import com.example.movieproject.domain.ChatRoom;
import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Review;
import com.example.movieproject.dto.request.ChatRoomCreateRequestDTO;
import com.example.movieproject.dto.request.ChatRoomUpdateRequestDTO;
import com.example.movieproject.dto.response.ChatRoomCreateResponseDTO;
import com.example.movieproject.dto.response.ChatRoomResponseDTO;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.ChatMessageRepository;
import com.example.movieproject.repository.ChatRoomRepository;
import com.example.movieproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatRoomCreateResponseDTO createChatRoom(Long memberId, ChatRoomCreateRequestDTO requestDTO){

        Member member = memberRepository.findById(memberId).orElseThrow();

        ChatRoom chatRoom = ChatRoomCreateRequestDTO.dtoToEntity(requestDTO);

        return ChatRoomCreateResponseDTO.entityToDTO(chatRoomRepository.save(chatRoom));
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDTO> getChatRoomList(){

        return chatRoomRepository.getChatRoomList().stream().map(ChatRoomResponseDTO::entityToDTO).toList();
    }


    @Transactional(readOnly = true)
    public ChatRoomResponseDTO getChatRoom(Long chatRoomId){
        return ChatRoomResponseDTO.entityToDTO(chatRoomRepository.getChatRoom(chatRoomId));
    }

    @Transactional
    public Boolean updateChatRoom(Long chatRoomId, Long memberId, ChatRoomUpdateRequestDTO updateRequestDTO){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        validate(chatRoom,memberId);

        chatRoom.changeChatRoom(updateRequestDTO);


        return true;
    }

    @Transactional
    public Boolean deleteChatRoom(Long chatRoomId,Long memberId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        validate(chatRoom,memberId);

        chatMessageRepository.deleteAllByChatRoom(chatRoom);

        chatRoomRepository.deleteById(chatRoomId);

        return true;
    }

    private void validate(ChatRoom chatRoom, Long memberId){
        if(Objects.equals(chatRoom.getMember().getMemberId(),memberId)){
            throw new ReviewException(ErrorList.NOT_EXIST_MEMBER);
        }
    }



}

package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.request.ChatRoomCreateRequestDTO;
import com.example.movieproject.dto.request.ChatRoomUpdateRequestDTO;
import com.example.movieproject.dto.response.ChatRoomCreateResponseDTO;
import com.example.movieproject.dto.response.ChatRoomResponseDTO;
import com.example.movieproject.service.ChatRoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ApiOperation(value = "")
    @PostMapping("/create")
    public ResponseEntity<ChatRoomCreateResponseDTO> createChatRoom(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ChatRoomCreateRequestDTO requestDTO
            ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(chatRoomService.createChatRoom(memberId,requestDTO));
    }

    @ApiOperation(value = "")
    @GetMapping("/getChatRoomList")
    public ResponseEntity<List<ChatRoomResponseDTO>> getChatRoomList(){

        return ResponseEntity.ok().body(chatRoomService.getChatRoomList());
    }

    @ApiOperation(value = "")
    @GetMapping("/getChatRoom/{chatRoomId}")
    public ResponseEntity<ChatRoomResponseDTO> getChatRoom(
            @PathVariable Long chatRoomId
    ){

        return ResponseEntity.ok().body(chatRoomService.getChatRoom(chatRoomId));
    }

    @ApiOperation(value = "")
    @PutMapping("/update/{chatRoomId}")
    public ResponseEntity<Boolean> updateChatRoom(
            @PathVariable Long chatRoomId,
            @RequestBody ChatRoomUpdateRequestDTO updateRequestDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){



        return ResponseEntity.ok().body(true);
    }

    @ApiOperation(value = "")
    @DeleteMapping("/delete/{chatRoomId}")
    public ResponseEntity<Boolean> deleteChatRoom(
            @PathVariable Long chatRoomId
    ){

        return ResponseEntity.ok().body(true);
    }





}

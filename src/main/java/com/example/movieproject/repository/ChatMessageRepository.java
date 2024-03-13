package com.example.movieproject.repository;

import com.example.movieproject.domain.ChatMessage;
import com.example.movieproject.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

      void deleteAllByChatRoom(ChatRoom chatRoom);


}

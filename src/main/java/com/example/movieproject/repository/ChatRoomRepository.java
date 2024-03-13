package com.example.movieproject.repository;

import com.example.movieproject.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    @Query("select cr from ChatRoom cr join fetch cr.member")
    List<ChatRoom> getChatRoomList();

    @Query("select cr from ChatRoom cr join fetch cr.member " +
            "where cr.chatRoomId = :chatRoomId ")
   ChatRoom getChatRoom(Long chatRoomId);

}

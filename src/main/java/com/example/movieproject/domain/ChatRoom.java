package com.example.movieproject.domain;

import com.example.movieproject.dto.request.ChatRoomUpdateRequestDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId")
    private Long chatRoomId;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    private String chatRoomTitle;

    private String chatRoomExplain;


    public void changeChatRoom(ChatRoomUpdateRequestDTO updateRequestDTO){
        this.chatRoomTitle = updateRequestDTO.getChatRoomTitle();
        this.chatRoomExplain = updateRequestDTO.getChatRoomExplain();
    }






}

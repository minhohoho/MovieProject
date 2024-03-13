package com.example.movieproject.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessageId")
    private Long chatMessageId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="chatRoomId")
    @ToString.Exclude
    private ChatRoom chatRoom;

    private String message;
}

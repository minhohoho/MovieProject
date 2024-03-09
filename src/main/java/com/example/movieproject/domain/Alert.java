package com.example.movieproject.domain;

import com.example.movieproject.common.type.AlertType;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alertId")
    private Long alertId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "memberId")
    private Member member;

    private String sender;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    public void changeIsRead(boolean isRead){
        this.isRead = isRead;
    }


}

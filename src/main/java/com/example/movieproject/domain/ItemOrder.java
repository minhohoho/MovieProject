package com.example.movieproject.domain;

import com.example.movieproject.common.type.OrderStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class ItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemOrderId")
    private Long itemOrderId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="memberId")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="cinemaItemId")
    @ToString.Exclude
    private CinemaItem cinemaItem;

    private int cnt;

    private int ItemTotalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void changeStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }


}

package com.example.movieproject.repository;

import com.example.movieproject.domain.ItemOrder;
import com.example.movieproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemOrderRepository extends JpaRepository<ItemOrder,Long>,ItemOrderCustomRepository {

    @Query("select io from ItemOrder io join fetch io.cinemaItem where io.member.memberId= :memberId " +
            "AND io.orderStatus = 'NOT_REFUND' ")
    List<ItemOrder> getMyOrderList(Long memberId);

    @Query("select io from ItemOrder io join fetch io.cinemaItem where io.itemOrderId= :itemOrderId " +
            "AND io.orderStatus = 'NOT_REFUND' ")
    ItemOrder refundItemOrder(Long itemOrderId);

    @Query("select io from ItemOrder io join fetch io.cinemaItem where io.member.memberId= :memberId " +
            "AND io.orderStatus = 'REFUND' ")
    List<ItemOrder> getRefundList(Long memberId);


}

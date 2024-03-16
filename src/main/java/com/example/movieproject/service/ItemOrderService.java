package com.example.movieproject.service;

import com.example.movieproject.common.type.OrderStatus;
import com.example.movieproject.domain.CinemaItem;
import com.example.movieproject.domain.ItemOrder;
import com.example.movieproject.domain.Member;
import com.example.movieproject.dto.request.ItemOrderCreateRequestDTO;
import com.example.movieproject.dto.response.MyItemOrderResponseDTO;
import com.example.movieproject.repository.CinemaItemRepository;
import com.example.movieproject.repository.ItemOrderRepository;
import com.example.movieproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemOrderService {

    private final ItemOrderRepository itemOrderRepository;
    private final MemberRepository memberRepository;
    private final CinemaItemRepository cinemaItemRepository;

    @Transactional
    public Integer createOrder(Long memberId, List<ItemOrderCreateRequestDTO> itemOrderCreateRequestDTOList){

        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        int itemListTotalPrice = 0;

        for(ItemOrderCreateRequestDTO requestDTO: itemOrderCreateRequestDTOList){

            CinemaItem item = cinemaItemRepository.findById(requestDTO.getCinemaItemId())
                    .orElseThrow();

            ItemOrder itemOrder = ItemOrder.builder()
                    .member(member)
                    .cinemaItem(item)
                    .cnt(requestDTO.getCnt())
                    .ItemTotalPrice(requestDTO.getCnt()* item.getPrice())
                    .orderStatus(OrderStatus.NOT_REFUND)
                    .build();
            itemListTotalPrice += requestDTO.getCnt()* item.getPrice();

            itemOrderRepository.save(itemOrder);
        }


        return itemListTotalPrice;
    }

    @Transactional(readOnly = true)
    public List<MyItemOrderResponseDTO>  getMyOrderList(Long memberId){

        List<ItemOrder> itemOrderList = itemOrderRepository.getMyOrderList(memberId);

        return itemOrderList.stream().map(myOrder->MyItemOrderResponseDTO.entityToDTO(myOrder,myOrder.getCinemaItem().getItemName())).toList();
    }

    @Transactional
    public MyItemOrderResponseDTO refundItemOrder(Long itemOrderId,Long memberId){

        ItemOrder itemOrder = itemOrderRepository.refundItemOrder(itemOrderId);

        validate(itemOrder,memberId);

        itemOrder.changeStatus(OrderStatus.REFUND);

        return MyItemOrderResponseDTO.entityToDTO(itemOrder,itemOrder.getCinemaItem().getItemName());
    }

    @Transactional(readOnly = true)
    public List<MyItemOrderResponseDTO>  getRefundList(Long memberId){

        List<ItemOrder> itemOrderList = itemOrderRepository.getRefundList(memberId);

        return itemOrderList.stream().map(myOrder->MyItemOrderResponseDTO.entityToDTO(myOrder,myOrder.getCinemaItem().getItemName())).toList();
    }



    private void validate(ItemOrder itemOrder, Long memberId){
        if(!Objects.equals(itemOrder.getMember().getMemberId(),memberId)){
            throw new RuntimeException("");
        }
    }


}

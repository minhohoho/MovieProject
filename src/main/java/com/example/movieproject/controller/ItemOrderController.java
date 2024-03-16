package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.request.ItemOrderCreateRequestDTO;
import com.example.movieproject.dto.response.MyItemOrderResponseDTO;
import com.example.movieproject.service.ItemOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class ItemOrderController {

    private final ItemOrderService itemOrderService;


    @ApiOperation(value = "영화관 상품 리스트 주문 api")
    @PostMapping("/create")
    public ResponseEntity<Integer> createOrder(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody List<ItemOrderCreateRequestDTO> itemOrderCreateRequestDTOList
            ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(itemOrderService.createOrder(memberId,itemOrderCreateRequestDTOList));
    }

    @ApiOperation(value = "본인의 주문 내역 List api")
    @GetMapping("/getMyOrderList")
    public ResponseEntity<List<MyItemOrderResponseDTO>> getMyOrderList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long memberId = userPrincipal.getId();;

        return ResponseEntity.ok().body(itemOrderService.getMyOrderList(memberId));
    }

    @ApiOperation(value = "본인의 주문 내역 환불 api")
    @PutMapping("/update/{itemOrderId}")
    public ResponseEntity<MyItemOrderResponseDTO> refundItemOrder(
            @PathVariable Long itemOrderId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){

        Long memberId = userPrincipal.getId();


        return ResponseEntity.ok().body(itemOrderService.refundItemOrder(itemOrderId,memberId));
    }

    @ApiOperation(value = "본인의 주문 내역 List api")
    @GetMapping("/getRefundList")
    public ResponseEntity<List<MyItemOrderResponseDTO>> getRefundList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long memberId = userPrincipal.getId();;

        return ResponseEntity.ok().body(itemOrderService.getRefundList(memberId));
    }




}

package com.example.movieproject.controller;

import com.example.movieproject.dto.request.CinemaItemUpdateRequestDTO;
import com.example.movieproject.service.CinemaItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cinemaItem")
public class CinemaItemController {

    private final CinemaItemService cinemaItemService;

    @ApiOperation(value = "상품 정보 수정")
    @PutMapping("/update/{cinemaItemId}")
    public ResponseEntity<Boolean> updateItemInfo(@PathVariable Long cinemaItemId, @RequestBody CinemaItemUpdateRequestDTO updateDTO){

        return ResponseEntity.ok().body(cinemaItemService.updateItemInfo(cinemaItemId,updateDTO));
    }

    @ApiOperation(value = "상품 정보 삭제")
    @DeleteMapping("/delete/{cinemaItemId}")
    public ResponseEntity<Boolean> deleteItemInfo(@PathVariable Long cinemaItemId){

        return ResponseEntity.ok().body(cinemaItemService.deleteItemInfo(cinemaItemId));
    }

    @ApiOperation(value = "상품 이미지 추가")
    @PutMapping("/upload/{cinemaItemId}")
    public ResponseEntity<String> uploadCinemaItemImage(
            @PathVariable Long cinemaItemId,
            @RequestBody MultipartFile multipartFile){


        return ResponseEntity.ok().body(cinemaItemService.uploadCinemaItemImage(cinemaItemId,multipartFile));
    }

}

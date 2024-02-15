package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 생성",notes = "가입된 회원들은 리뷰를 달 수 있다")
    @PostMapping("/create/{movieId}")
    public ResponseEntity<ReviewCreateResponseDTO> createReview(
            @RequestBody ReviewCreateRequestDTO requestDTO,
            @PathVariable Long movieId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long memberId = userPrincipal.getId();
        return ResponseEntity.ok().body(reviewService.createReview(requestDTO,movieId,memberId));
    }



}

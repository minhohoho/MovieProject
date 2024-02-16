package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.dto.response.ReviewResponse;
import com.example.movieproject.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 생성",notes = "가입된 회원들은 리뷰를 달 수 있다")
    @PostMapping("/create/{movieId}")
    @Secured({"USER","ADMIN"})
    public ResponseEntity<ReviewCreateResponseDTO> createReview(
            @RequestBody ReviewCreateRequestDTO requestDTO,
            @PathVariable Long movieId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long memberId = userPrincipal.getId();
        return ResponseEntity.ok().body(reviewService.createReview(requestDTO,movieId,memberId));
    }

    @ApiOperation(value="리뷰 단건 조회",notes="")
    @GetMapping("/getReview/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId){

        return ResponseEntity.ok().body(reviewService.getReview(reviewId));
    }
    @ApiOperation(value="영화 한편의 모든 리뷰 조회",notes="")
    @GetMapping("/getReviewList/{movieId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewList(
            @PathVariable Long movieId,
             Pageable pageable
    ){
        return ResponseEntity.ok().body(reviewService.getReviewList(movieId, pageable));
    }







}

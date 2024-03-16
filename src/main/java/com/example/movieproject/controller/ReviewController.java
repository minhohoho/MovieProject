package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.request.ReviewUpdateRequestDTO;
import com.example.movieproject.dto.response.MyReviewListResponseDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.dto.response.ReviewLikeResponse;
import com.example.movieproject.dto.response.ReviewResponse;
import com.example.movieproject.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 생성  api",notes = "가입된 회원들은 리뷰를 달 수 있다")
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

    @ApiOperation(value="리뷰 단건 조회 api",notes="영화에 대한 리뷰를 확인 할 수 있다")
    @GetMapping("/getReview/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId){

        return ResponseEntity.ok().body(reviewService.getReview(reviewId));
    }
    @ApiOperation(value="영화 한편의 모든 리뷰 조회 api",notes="해당 영화의 모든 리뷰를 확인 할 수 있다")
    @GetMapping("/getReviewList/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getReviewList(
            @PathVariable Long movieId
    ){
        return ResponseEntity.ok().body(reviewService.getReviewList(movieId));
    }

    @ApiOperation(value="리뷰 좋아요 api",notes = "로그인을 한 회원이라면 리뷰에 좋아요를 달 수 있다")
    @PostMapping("/reviewLike/{reviewId}")
    public ResponseEntity<ReviewLikeResponse> reviewLike(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(reviewService.reviewLike(reviewId,memberId));
    }

    @ApiOperation(value = "회원이 작성한 리뷰 조회 api",notes = "권한 인증이 된 회원은 자기가 작성한 모든 리뷰를 읽을 수 있다")
    @GetMapping("/getMyReviewList")
    public ResponseEntity<MyReviewListResponseDTO> getMemberReviewList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(reviewService.getMemberReviewList(memberId));
    }

    @ApiOperation(value= "다른 회원이 작성한 리뷰 조회 api",notes = "")
    @GetMapping("/getOtherReviewList/{memberId}")
    public ResponseEntity<MyReviewListResponseDTO>  getOtherReviewList(@PathVariable Long memberId){

        return ResponseEntity.ok().body(reviewService.getMemberReviewList(memberId));
    }


    @ApiOperation(value="리뷰 정보 수정 api",notes = "로그인이 된 회원이라면 자기가 작성한 리뷰 내용을 수정할 수 있다")
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<Void> updateReviewInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDTO updateDTO
            ){

        Long memberId = userPrincipal.getId();

        reviewService.updateReviewInfo(reviewId,memberId,updateDTO);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value="리뷰 정보 삭제 api",notes = "로그인이 된 회원이라면 자기가 작성한 리뷰를 삭제 할 수 있다")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long reviewId
    ){

        Long memberId = userPrincipal.getId();

        reviewService.deleteReview(reviewId,memberId);

        return ResponseEntity.ok().build();
    }





}

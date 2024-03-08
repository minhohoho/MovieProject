package com.example.movieproject.service;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Review;
import com.example.movieproject.domain.ReviewLike;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.request.ReviewUpdateRequestDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.dto.response.ReviewLikeResponse;
import com.example.movieproject.dto.response.ReviewResponse;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.ReviewLikeRepository;
import com.example.movieproject.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReviewLikeRepository reviewLikeRepository;

    @DisplayName("[리뷰 생성]-성공")
    @Test
    void createReview(){

        //given
        Movie movie = Movie.builder()
                .MovieId(1L)
                .title("test")
                .build();

        Member member = Member.builder()
                .memberId(1L)
                .name("석민호")
                .build();

        Review review = Review.builder()
                .reviewId(1L)
                .movie(movie)
                .member(member)
                .content("test")
                .build();

        ReviewCreateRequestDTO requestDTO = ReviewCreateRequestDTO.builder()
                .content("test")
                .build();

        given(movieRepository.findById(movie.getMovieId()))
                .willReturn(Optional.of(movie));

        given(memberRepository.findById(member.getMemberId()))
                .willReturn(Optional.of(member));

        given(reviewRepository.save(any())).willReturn(review);

        //when
        ReviewCreateResponseDTO responseDTO = reviewService.createReview(requestDTO,1L,1L);

        assertEquals(1L,responseDTO.getReviewId());
    }

    @DisplayName("[리뷰 조회]-성공")
    @Test
    void getReview(){
        Member member = Member.builder()
                .memberId(1L)
                .name("석민호")
                .build();

        //given
        Review review = Review.builder()
                .reviewId(1L)
                .member(member)
                .content("test")
                .score(3.4)
                .build();

        given(reviewRepository.findReviewInfo(any()))
                .willReturn(review);

        //when
        ReviewResponse reviewResponse = reviewService.getReview(1L);

        assertEquals(1L,reviewResponse.getReviewId());
    }

    @DisplayName("[리뷰 조회]-해당 리뷰가 존재하지 않음")
    @Test
    void failGetReview(){

        Review review = null;

        given(reviewRepository.findReviewInfo(any())).willReturn(review);

        ReviewException reviewException = assertThrows(ReviewException.class,
                ()->reviewService.getReview(1L));

        assertEquals(reviewException.getErrorMessage(), ErrorList.NOT_EXIST_REVIEW.getErrorMessage());
    }

    @DisplayName("[리뷰 좋아요]-성공")
    @Test
    void createReviewLike(){
        //given
        Member reviewer = Member.builder()
                .memberId(1L)
                .name("석민호")
                .build();

        Member reviewLiker = Member.builder()
                .memberId(2L)
                .name("이민호")
                .build();


        Review review = Review.builder()
                .reviewId(1L)
                .member(reviewer)
                .build();



        given(reviewRepository.findById(any())).willReturn(Optional.of(review));
        given(memberRepository.findById(any())).willReturn(Optional.of(reviewLiker));

        given(reviewLikeRepository.findByReviewAndMember(review,reviewLiker)).willReturn(Optional.empty());

        //when
        ReviewLikeResponse reviewLikeResponse = reviewService.reviewLike(1L,2L);


        //then
        assertEquals(true,reviewLikeResponse.isResult());
        assertEquals("좋아요 성공!",reviewLikeResponse.getMessage());
    }







}

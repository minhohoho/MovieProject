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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    private final ReviewLikeRepository reviewLikeRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public ReviewCreateResponseDTO createReview(ReviewCreateRequestDTO requestDTO,Long movieId,Long memberId){

        movieRepository.findById(movieId)
                .orElseThrow(()-> new ReviewException(ErrorList.NOT_EXIST_MOVIE));

        Review review = ReviewCreateRequestDTO.dtoToEntity(requestDTO,movieId,memberId);

        return ReviewCreateResponseDTO.EntityToDTO(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public double averageScore(Long movieId){
        return reviewRepository.averageMovieScore(movieId);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewId){
        Review review = reviewRepository.findReviewInfo(reviewId);
        return  ReviewResponse.EntityToDTO(review);
    }



    @Transactional
    public ReviewLikeResponse reviewLike(Long reviewId, Long memberId){

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_REVIEW));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MEMBER));
        ReviewLikeResponse reviewLikeResponse = new ReviewLikeResponse();

        Optional<ReviewLike> reviewLike = reviewLikeRepository.findByReviewAndMember(review,member);

        if(reviewLike.isEmpty()) {
            reviewLikeRepository.save(ReviewLike.builder()
                            .review(review)
                            .member(member)
                    .build());
            reviewLikeResponse.setResult(true);
            reviewLikeResponse.setMessage("좋아요 성공!");
            return reviewLikeResponse;
        }
           reviewLikeRepository.delete(reviewLike.get());
           reviewLikeResponse.setResult(false);
           reviewLikeResponse.setMessage("좋아요 취소 성공!");

        return reviewLikeResponse;
    }
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewList(Long movieId){

        Movie movie = movieRepository.findById(movieId).orElseThrow();

        return reviewRepository.findReviewList(movie).stream().map(ReviewResponse::EntityToDTO).toList();
    }

    @Transactional(readOnly = true)
    public void getMyReviewList(Long memberId){}

    @Transactional
    public void updateReviewInfo(Long reviewId, Long memberId, ReviewUpdateRequestDTO updateDTO){
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        validate(review,memberId);

        review.updateReview(updateDTO);
    }

    @Transactional
    public void deleteReview(Long reviewId,Long memberId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_REVIEW));

        validate(review,memberId);
        reviewRepository.deleteById(review.getReviewId());
    }

    private void validate(Review review,Long memberId){
        if(Objects.equals(review.getMember().getMemberId(),memberId)){
         throw new RuntimeException();
        }
    }


}

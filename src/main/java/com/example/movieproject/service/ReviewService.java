package com.example.movieproject.service;

import com.example.movieproject.common.type.AlertType;
import com.example.movieproject.domain.*;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.request.ReviewUpdateRequestDTO;
import com.example.movieproject.dto.response.MyReviewListResponseDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.dto.response.ReviewLikeResponse;
import com.example.movieproject.dto.response.ReviewResponse;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.ReviewLikeRepository;
import com.example.movieproject.repository.ReviewRepository;
import com.example.movieproject.repository.alert.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

    private final AlertRepository alertRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReviewCreateResponseDTO createReview(ReviewCreateRequestDTO requestDTO,Long movieId,Long memberId){

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new ReviewException(ErrorList.NOT_EXIST_MOVIE));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ReviewException(ErrorList.NOT_EXIST_MEMBER));

        int count = member.getCount(); // 멤버가 현재까지 평가한 영화 수 삭제
        count++;
        log.info(String.valueOf(count));
        member.changeCount(count);

        Review review = ReviewCreateRequestDTO.dtoToEntity(requestDTO,movie,member);

        return ReviewCreateResponseDTO.EntityToDTO(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public double averageScore(Long movieId){
        return reviewRepository.averageMovieScore(movieId);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewId){
        Review review = reviewRepository.findReviewInfo(reviewId);

        if(ObjectUtils.isEmpty(review))  {
            throw new ReviewException(ErrorList.NOT_EXIST_REVIEW);
        }

        return  ReviewResponse.EntityToDTO(review);
    }
    @Transactional
    public ReviewLikeResponse reviewLike(Long reviewId, Long memberId){

        // ex) 1번 회원의 리뷰
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_REVIEW));

        //리뷰를 작성한 회원에 대한 정보
        Long reviewMemberId = review.getMember().getMemberId(); // 현재는 fetch 조인을 하지 않아 n+1 문제가 남

        //리뷰를 작성한 회원 객체 생성
        Member reviewMember = memberRepository.findById(reviewMemberId)
                .orElseThrow();


        // ex) 1번 회원의 리뷰에 대한 좋아요를 누를 회원
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MEMBER));
        ReviewLikeResponse reviewLikeResponse = new ReviewLikeResponse();

        Optional<ReviewLike> reviewLike = reviewLikeRepository.findByReviewAndMember(review,member);

        if(reviewLike.isEmpty()) {
            reviewLikeRepository.save(ReviewLike.builder()
                            .review(review)
                            .member(member)
                    .build());

            int reviewCnt = review.getReviewCnt();;
            reviewCnt++;
            review.ChangeReviewCnt(reviewCnt);

            reviewLikeResponse.setResult(true);
            reviewLikeResponse.setMessage("좋아요 성공!");


            // ex) 리뷰를 작성한 1번회원에게 좋아요 +1 전달
            Alert savedAlert = Alert.builder()
                    .member(reviewMember)
                    .sender(member.getName())
                    .isRead(false)
                    .alertType(AlertType.REVIEW_LIKE)
                    .build();

            alertRepository.save(savedAlert);

            eventPublisher.publishEvent(savedAlert);

            return reviewLikeResponse;
        }
           reviewLikeRepository.delete(reviewLike.get());

           int reviewCnt = review.getReviewCnt();
           reviewCnt--;
           review.ChangeReviewCnt(reviewCnt);


           reviewLikeResponse.setResult(false);
           reviewLikeResponse.setMessage("좋아요 취소 성공!");

        return reviewLikeResponse;
    }
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewList(Long movieId){

        Movie movie = movieRepository.findById(movieId).orElseThrow();

        return reviewRepository.findReviewList(movie).stream().map(ReviewResponse::EntityToDTO).toList();
    }


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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MEMBER));

        int count = member.getCount(); // 멤버가 현재까지 평가한 영화 숫자 --
        count --;
        log.info(String.valueOf(count));
        member.changeCount(count);

        reviewLikeRepository.deleteByReview(review); // 해당 리뷰의 좋아요 모두 삭제

        reviewRepository.deleteById(review.getReviewId()); //해당 리뷰 삭제
    }

    @Transactional(readOnly = true)
    public MyReviewListResponseDTO getMemberReviewList(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MEMBER));

        int count = member.getCount();
        List<ReviewResponse> responseDTOList = reviewRepository.findByMember(member).stream().map(ReviewResponse::EntityToDTO).toList();

        MyReviewListResponseDTO myReviewListResponseDTO = MyReviewListResponseDTO.builder()
                .count(count)
                .reviewResponseList(responseDTOList)
                .build();

        return myReviewListResponseDTO;
    }




    private void validate(Review review,Long memberId){
        if(Objects.equals(review.getMember().getMemberId(),memberId)){
         throw new ReviewException(ErrorList.NOT_EXIST_MEMBER);
        }
    }
}

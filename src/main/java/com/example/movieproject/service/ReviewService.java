package com.example.movieproject.service;

import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Review;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.dto.response.ReviewCreateResponseDTO;
import com.example.movieproject.dto.response.ReviewResponse;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    @Transactional
    public ReviewCreateResponseDTO createReview(ReviewCreateRequestDTO requestDTO,Long movieId,Long memberId){

        movieRepository.findById(movieId).orElseThrow(()-> new ReviewException(ErrorList.NOT_EXIST_MOVIE));

        Review review = ReviewCreateRequestDTO.dtoToEntity(requestDTO,movieId,memberId);

        return ReviewCreateResponseDTO.EntityToDTO(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public double averageScore(Long movieId){
        return reviewRepository.averageMovieScore(movieId);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        return  ReviewResponse.EntityToDTO(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewList(Long movieId, Pageable pageable){

        log.info(movieId.toString());

        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MOVIE));

        log.info(movie.toString());

        return reviewRepository.findAllByMovie(movie,pageable).map(ReviewResponse::EntityToDTO);
    }













}

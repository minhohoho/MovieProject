package com.example.movieproject.repository;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Review;
import com.example.movieproject.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike,Long> {

     Optional<ReviewLike> findByReviewAndMember(Review  review, Member member);

     void deleteByReview(Review review);


}

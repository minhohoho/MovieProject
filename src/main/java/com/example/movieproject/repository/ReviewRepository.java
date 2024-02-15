package com.example.movieproject.repository;

import com.example.movieproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review,Long> {

   @Query("select avg (r.score) from Review r where r.MovieId.MovieId= :movieId")
   Double averageMovieScore(Long movieId);

}

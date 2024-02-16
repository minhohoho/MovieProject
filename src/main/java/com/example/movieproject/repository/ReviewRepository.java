package com.example.movieproject.repository;

import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReviewRepository extends JpaRepository<Review,Long> {

   @Query("select avg (r.score) from Review r where r.movie.MovieId= :movieId")
   Double averageMovieScore(Long movieId);

   Page<Review> findAllByMovie(Movie movie,Pageable pageable);





}

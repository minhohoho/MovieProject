package com.example.movieproject.repository;

import com.example.movieproject.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {


}

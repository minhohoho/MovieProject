package com.example.movieproject.repository;

import com.example.movieproject.domain.MyCinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCinemaRepository extends JpaRepository<MyCinema,Long> {

}

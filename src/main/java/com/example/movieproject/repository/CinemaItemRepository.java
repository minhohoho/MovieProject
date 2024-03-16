package com.example.movieproject.repository;

import com.example.movieproject.domain.CinemaItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaItemRepository extends JpaRepository<CinemaItem,Long>,CinemaItemCustomRepository {
}

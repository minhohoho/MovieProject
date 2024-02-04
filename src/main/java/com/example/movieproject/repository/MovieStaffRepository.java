package com.example.movieproject.repository;

import com.example.movieproject.domain.MovieStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieStaffRepository extends JpaRepository<MovieStaff,Long>,MovieStaffCustomRepository {
}

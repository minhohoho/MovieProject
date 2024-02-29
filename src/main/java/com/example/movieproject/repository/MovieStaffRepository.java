package com.example.movieproject.repository;

import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MovieStaff;
import com.example.movieproject.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieStaffRepository extends JpaRepository<MovieStaff,Long>,MovieStaffCustomRepository {

    void deleteByStaff(Staff staff);
    void deleteByMovie(Movie movie);


}

package com.example.movieproject.repository;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.MyCinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CinemaScheduleRepository extends JpaRepository<CinemaSchedule,Long> {

    void deleteAllByMyCinema(MyCinema myCinema);

    List<CinemaSchedule> findByMyCinema(MyCinema myCinema);

    @Query("select c from CinemaSchedule c " +
            "join fetch c.movie " +
            "where c.myCinema = :myCinema AND c.showDate > :now")
    List<CinemaSchedule> findSchedule(MyCinema myCinema,Date now);







}

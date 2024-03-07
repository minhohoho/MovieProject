package com.example.movieproject.repository;

import com.example.movieproject.domain.Application;
import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    @Query("select a from Application a join fetch a.member join fetch a.cinemaSchedule where a.applicationId= :applicationId")
    Application findByApplication(Long applicationId);

    @Query("select a from Application a " +
            "join fetch a.cinemaSchedule " +
            "where a.member= :member " +
            "AND a.createdAt BETWEEN :startDate AND :endDate")
    List<Application> findMyApplicationList(Member member, LocalDateTime startDate,LocalDateTime endDate);

    void deleteAllByCinemaSchedule(CinemaSchedule cinemaSchedule);






}

package com.example.movieproject.repository;

import com.example.movieproject.domain.CinemaSchedule;
import com.example.movieproject.domain.MyCinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CinemaScheduleRepository extends JpaRepository<CinemaSchedule,Long>{

    void deleteAllByMyCinema(MyCinema myCinema);


    @Query("select c from CinemaSchedule c " +
            "join fetch c.movie " +
            "where c.myCinema = :myCinema AND c.showDate > :now")
    List<CinemaSchedule> findSchedule(MyCinema myCinema,Date now);

    @Query("select year(cs.showDate), sum(cs.price * cs.headCount) " +
            "from CinemaSchedule cs " +
            "where cs.myCinema.myCinemaId= :myCinemaId " +
            "group by year(cs.showDate)")
    List<Object[]> getYearIncome(Long myCinemaId);

    @Query("select year(cs.showDate), month(cs.showDate) ,sum(cs.price * cs.headCount) " +
            "from CinemaSchedule cs " +
            "where cs.myCinema.myCinemaId= :myCinemaId " +
            "group by year(cs.showDate),month(cs.showDate)")
    List<Object[]> getYearAndMonth(Long myCinemaId);

    @Query("select year(cs.showDate), month(cs.showDate) , week(cs.showDate) ,sum(cs.price * cs.headCount) " +
            "from CinemaSchedule cs " +
            "where cs.myCinema.myCinemaId= :myCinemaId " +
            "group by year(cs.showDate),month(cs.showDate), week(cs.showDate)")
    List<Object[]> getYearAndMonthAndWeek(Long myCinemaId);

    List<CinemaSchedule> findByMyCinema(MyCinema myCinema);




}

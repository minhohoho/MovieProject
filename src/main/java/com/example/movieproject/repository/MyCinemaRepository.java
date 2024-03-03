package com.example.movieproject.repository;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyCinemaRepository extends JpaRepository<MyCinema,Long> {

    List<MyCinema> findByMember(Member member);

    @Query(" select m from MyCinema m join fetch m.member where m.myCinemaId= :myCinemaId")
    MyCinema findMyCinemaInfo(Long myCinemaId);


}

package com.example.movieproject.repository.alert;

import com.example.movieproject.domain.Alert;
import com.example.movieproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert,Long> {

    @Query("select a from Alert a where a.isRead = false")
    List<Alert> findByMemberInfo(Member member);





}

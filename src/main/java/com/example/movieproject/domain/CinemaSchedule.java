package com.example.movieproject.domain;

import com.example.movieproject.common.type.CinemaScheduleStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class CinemaSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaScheduleId")
    private Long cinemaScheduleId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="myCinemaId")
    @ToString.Exclude
    private MyCinema myCinema;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="movieId")
    @ToString.Exclude
    private Movie movie;

    @Temporal(TemporalType.TIMESTAMP)
    private Date showDate;

    private Integer price;

    private Integer headCount;

    private Integer limitCount;

    @Enumerated(EnumType.STRING)
    private CinemaScheduleStatus cinemaScheduleStatus;

    public void changeStatus(CinemaScheduleStatus cinemaScheduleStatus){
        this.cinemaScheduleStatus=cinemaScheduleStatus;
    }

    public void addHeadCount(Integer headCount){
        this.headCount=headCount;
    }



}

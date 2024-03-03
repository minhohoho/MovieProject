package com.example.movieproject.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table
public class Application extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="applicationId")
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "cinemaScheduleId")
    private CinemaSchedule  cinemaSchedule;

}

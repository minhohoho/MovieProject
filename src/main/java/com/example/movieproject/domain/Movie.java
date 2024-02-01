package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movieId")
    private Long MovieId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date openingDate;

    @Column(nullable = false)
    private String duringTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieTheme movieTheme;

    @Lob
    @Column(nullable = false)
    private String content;





}

package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MovieId;

    @OneToMany(mappedBy = "movie")
    private List<Movie_Staff> movieStaffList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Age age;

    @Temporal(TemporalType.DATE)
    private Date openingDate;

    @Temporal(TemporalType.TIME)
    private Date duringTime;

    @Enumerated(EnumType.STRING)
    private MovieTheme movieTheme;

    @Lob
    private String content;
}

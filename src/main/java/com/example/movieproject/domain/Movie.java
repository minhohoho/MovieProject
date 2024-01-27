package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import lombok.*;

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
@ToString
public class Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movieId")
    private Long MovieId;

    @OneToMany(mappedBy = "movie")
    private List<Movie_Staff> movieStaffList = new ArrayList<>();

    private String title;

    @Enumerated(EnumType.STRING)
    private Age age;

    @Temporal(TemporalType.DATE)
    private Date openingDate;

    private String duringTime;

    @Enumerated(EnumType.STRING)
    private MovieTheme movieTheme;

    @Lob
    private String content;
}

package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.dto.request.MovieUpdateRequestDTO;
import lombok.*;
import javax.persistence.*;
import java.util.Date;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(indexes = {@Index(columnList = "openingDate")})
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

    public void updateMovie(MovieUpdateRequestDTO updateDTO){
     this.title=updateDTO.getTitle();
     this.age=updateDTO.getAge();
     this.openingDate=updateDTO.getOpeningDate();
     this.duringTime=updateDTO.getDuringTime();
     this.movieTheme=updateDTO.getMovieTheme();
     this.content=updateDTO.getContent();
    }



}

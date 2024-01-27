package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.repository.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(SpringExtension.class)
public class MovieJpaTest {

    @Autowired
    private MovieRepository movieRepository;

    @DisplayName("영화 등록 테스트")
    @Test
    void GivenMovieInfo_WhenInserting_ThenWorksFine(){

        //given
        String title="헝거게임";
        Date openDate = new Date(2012,10,10);
        String duringTime="2시간 1분";


        //when
        Movie movie = movieRepository.save(Movie.builder()
                        .title(title)
                        .age(Age.FIFTEEN)
                        .openingDate(openDate)
                        .duringTime(duringTime)
                        .movieTheme(MovieTheme.SF)
                .build());


        //then
        assertEquals(title,movie.getTitle());
        assertEquals(Age.FIFTEEN,movie.getAge());
        assertEquals(duringTime,movie.getDuringTime());
    }
}

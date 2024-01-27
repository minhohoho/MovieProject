package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.Movie_StaffRepository;
import com.example.movieproject.repository.StaffRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(SpringExtension.class)
public class Movie_StaffJpaTest{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private Movie_StaffRepository movieStaffRepository;



    @DisplayName("")
    @Test
    void GivenMovieAndStaff_WhenInserting_ThenWorksFine(){

        //given
        Movie savedMovie= createMovie();
        Staff savedStaff= createStaff();

        //when
        Movie_Staff movieStaff = movieStaffRepository.save(Movie_Staff.builder()
                .movie(savedMovie)
                .staff(savedStaff)
                .role(Role.MAIN_ACTOR)
                .build());


        //then
        assertEquals(1L,savedMovie.getMovieId());
        assertEquals(Role.MAIN_ACTOR,movieStaff.getRole());
    }




    private Movie createMovie(){
        String title="헝거게임";
        Date openDate = new Date(2012,10,10);
        String duringTime="2시간 1분";

        return  movieRepository.save(Movie.builder()
                .title(title)
                .age(Age.FIFTEEN)
                .openingDate(openDate)
                .duringTime(duringTime)
                .movieTheme(MovieTheme.SF)
                .build());
    }

    private Staff createStaff(){
        String name="하정우";

        return staffRepository.save(Staff.builder()
                .name(name)
                .build());
    }


}

package com.example.movieproject.domain;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MovieStaffRepository;
import com.example.movieproject.repository.StaffRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    private MovieStaffRepository movieStaffRepository;



    @DisplayName("")
    @Test
    void GivenMovieAndStaff_WhenInserting_ThenWorksFine(){

        //given
        Movie savedMovie= createMovie();
        Staff savedStaff= createStaff();

        //when
        MovieStaff movieStaff = movieStaffRepository.save(MovieStaff.builder()
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

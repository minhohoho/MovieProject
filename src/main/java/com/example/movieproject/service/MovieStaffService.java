package com.example.movieproject.service;

import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.MovieStaff;
import com.example.movieproject.domain.Staff;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MovieStaffRepository;
import com.example.movieproject.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieStaffService {

    private final MovieStaffRepository movieStaffRepository;
    private final StaffRepository staffRepository;
    private final MovieRepository movieRepository;

    @Transactional
    public Boolean createMovieStaff(Long MovieId, Long StaffId, Role role){

        Movie movie = movieRepository.findById(MovieId).orElseThrow();
        Staff staff = staffRepository.findById(StaffId).orElseThrow();

        MovieStaff movieStaff = MovieStaff.builder()
                .movie(movie)
                .staff(staff)
                .role(role)
                .build();

        movieStaffRepository.save(movieStaff);

        return true;
    }


    @Transactional
    public Boolean updateMovieStaff(Long MovieStaffId,Role role){

        MovieStaff movieStaff = movieStaffRepository.findById(MovieStaffId).orElseThrow();

        movieStaff.updateMovieStaff(role);

        return true;
    }

    @Transactional
    public Boolean deleteMovieStaff(Long MovieStaffId){

        movieStaffRepository.deleteById(MovieStaffId);

        return true;
    }



}

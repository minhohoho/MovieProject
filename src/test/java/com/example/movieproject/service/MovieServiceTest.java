package com.example.movieproject.service;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.dto.response.MovieStaffResponseDTO;
import com.example.movieproject.dto.response.StaffResponseDTO;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.MovieException;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MovieStaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("영화 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks MovieService movieService;

    @Mock private MovieStaffRepository movieStaffRepository;
    @Mock private MovieRepository movieRepository;

    MovieStaffResponseDTO movieStaffResponseDTO;
    List<StaffResponseDTO> staffResponseDTOs=new ArrayList<>();


    @BeforeEach
    void beforeEach(){
        StaffResponseDTO staffResponseDTO = StaffResponseDTO.builder()
                .name("로다주")
                .role(Role.MAIN_ACTOR)
                .build();

        staffResponseDTOs.add(staffResponseDTO);


        movieStaffResponseDTO = MovieStaffResponseDTO.builder()
                .movieId(1L)
                .title("인디아나 존스")
                .age(Age.ALL)
                .duringTime("2시간 3분")
                .movieTheme(MovieTheme.SF)
                .staffResponseDTOList(staffResponseDTOs)
                .build();
    }


//    @DisplayName("[영화 조회]-영화 조회 시 영화 id가 잘못된 경우")
//    @Test
//    void givenMovieId_whenSelecting_thenReturnsThrows(){
//
//        //given
//        given(movieRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        //when
//        MovieException movieException = assertThrows(MovieException.class,
//                ()->movieService.getMovie(1L,3.5));
//
//        assertEquals(ErrorList.NOT_EXIST_MOVIE,movieException.getErrorList());
//    }







}
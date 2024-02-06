package com.example.movieproject.controller;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.dto.response.MovieListResponseDTO;
import com.example.movieproject.dto.response.MovieStaffResponseDTO;
import com.example.movieproject.dto.response.StaffResponseDTO;
import com.example.movieproject.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(MovieController.class)
@DisplayName("영화 컨트롤러 테스트")
class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

     Movie movie;
     MovieCreateRequestDTO movieCreateRequestDTO;
     MovieCreateResponseDTO movieCreateResponseDTO;
     Page<MovieListResponseDTO> movieResponseDTOPage;
     PageRequest pageable;
     MovieStaffResponseDTO movieStaffResponseDTO;
     List<StaffResponseDTO> staffResponseDTOs=new ArrayList<>();

    Date openDate = new Date(2012, 10, 10);

    @BeforeEach
    void beforeEach() {


        movie = Movie.builder()
                .title("인디아나존스")
                .age(Age.ALL)
                .openingDate(openDate)
                .duringTime("2시간 3분")
                .movieTheme(MovieTheme.SF)
                .content("test")
                .build();


        movieCreateRequestDTO = MovieCreateRequestDTO.builder()
                .title("인디아나존스")
                .age(Age.ALL)
                .openingDate(openDate)
                .duringTime("2시간 3분")
                .movieTheme(MovieTheme.SF)
                .content("test")
                .build();

        movieCreateResponseDTO = MovieCreateResponseDTO.builder()
                .title("인디아나존스")
                .age(Age.ALL)
                .openingDate(openDate)
                .duringTime("2시간 3분")
                .movieTheme(MovieTheme.SF)
                .content("test")
                .build();

        pageable = PageRequest.of(0, 20);
        movieResponseDTOPage = new PageImpl<>(List.of(MovieListResponseDTO.EntityToDTO(movie)), pageable, 0);


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


    @DisplayName("[영화 등록]-영화 정보와 스태프 정보를 각각의 엔티티에 저장한다")
    @Test
    void GivenMovieAndStaff_WhenInserting_ThenSavesMovieAndStaff() throws Exception {

        //given
        String requestJson = objectMapper.writeValueAsString(movieCreateRequestDTO);

        //when
        when(movieService.createMovie(any())).thenReturn(movieCreateResponseDTO);


        //then
        mockMvc.perform(post("/api/movie/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(movieCreateResponseDTO.getTitle()))
                .andExpect(jsonPath("$.duringTime").value(movieCreateResponseDTO.getDuringTime()));
    }


    @DisplayName("[영화 전체 검색]-get 요청이 오면 영화 검색 결과를 페이징해서 리턴한다.")
    @Test
    void givenNoting_whenSelecting_thenReturnsPaging() throws Exception {

        //given
        given(movieService.getList(any(),any())).willReturn(movieResponseDTOPage);

        //when && then
        mockMvc.perform(get("/api/movie/searchMovieList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].movieId").value(movie.getMovieId()))
                .andExpect(jsonPath("$.content[0].title").value(movie.getTitle()));
    }

    @DisplayName("[영화 단건 검색]-get 요청이 오면 한편의 영화에 대한 정보를  리턴한다")
    @Test
    void givenMovieId_whenSelecting_thenReturnsMovieStaff() throws  Exception{
     //given
     given(movieService.getMovie(anyLong())).willReturn(movieStaffResponseDTO);

     //when && then
     mockMvc.perform(get("/api/movie/getMovie/1")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(movieStaffResponseDTO)))
             .andExpect(status().isOk());

    }



}






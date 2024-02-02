package com.example.movieproject.controller;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.dto.response.MovieResponseDTO;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    private Movie movie;
    private MovieCreateRequestDTO movieCreateRequestDTO;
    private MovieCreateResponseDTO movieCreateResponseDTO;
    private Page<MovieResponseDTO> movieResponseDTOPage;
    private PageRequest pageable;
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
        movieResponseDTOPage = new PageImpl<>(List.of(MovieResponseDTO.EntityToDTO(movie)), pageable, 0);

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
        given(movieService.getList(any())).willReturn(movieResponseDTOPage);

        mockMvc.perform(get("/api/movie/getList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].movieId").value(movie.getMovieId()))
                .andExpect(jsonPath("$.content[0].title").value(movie.getTitle()));
    }
}






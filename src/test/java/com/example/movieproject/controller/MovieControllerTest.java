package com.example.movieproject.controller;

import com.example.movieproject.common.type.Age;
import com.example.movieproject.common.type.MovieTheme;
import com.example.movieproject.dto.request.MovieCreateRequestDTO;
import com.example.movieproject.dto.response.MovieCreateResponseDTO;
import com.example.movieproject.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(MovieController.class)
@DisplayName("영화 컨트롤러 테스트")
class MovieControllerTest {

    @MockBean private MovieService movieService;

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

  @DisplayName("[영화 등록]-영화 정보와 스태프 정보를 각각의 엔티티에 저장한다")
  @Test
  void GivenMovieAndStaff_WhenInserting_ThenSavesMovieAndStaff() throws Exception {

      //given
      Date openDate = new Date(2012,10,10);

      MovieCreateRequestDTO requestDTO = MovieCreateRequestDTO.builder()
            .title("인디아나존스")
            .age(Age.ALL)
            .openingDate(openDate)
            .duringTime("2시간 3분")
            .movieTheme(MovieTheme.SF)
            .content("test")
            .build();

      MovieCreateResponseDTO responseDTO = MovieCreateResponseDTO.builder()
              .title("인디아나존스")
              .age(Age.ALL)
              .openingDate(openDate)
              .duringTime("2시간 3분")
              .movieTheme(MovieTheme.SF)
              .content("test")
              .build();


      //when
      when(movieService.createMovie(any())).thenReturn(responseDTO);
      String requestJson = objectMapper.writeValueAsString(requestDTO);

      //then
      mockMvc.perform(post("/api/movie/create")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestJson))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.title").value(responseDTO.getTitle()))
              .andExpect(jsonPath("$.duringTime").value(responseDTO.getDuringTime()));
  }





}
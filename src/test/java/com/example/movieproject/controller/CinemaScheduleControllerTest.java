package com.example.movieproject.controller;

import com.example.movieproject.common.jwt.JwtProvider;
import com.example.movieproject.common.jwt.handler.JwtAccessDeniedHandler;
import com.example.movieproject.common.jwt.handler.JwtAuthenticationEntryPoint;
import com.example.movieproject.common.oauth2.handler.OAuth2FailHandler;
import com.example.movieproject.common.oauth2.handler.OAuth2SuccessHandler;
import com.example.movieproject.common.type.CinemaScheduleStatus;
import com.example.movieproject.config.SecurityConfig;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleListResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleResponseDTO;
import com.example.movieproject.service.CinemaScheduleService;
import com.example.movieproject.service.CustomOAuth2Service;
import com.example.movieproject.testUtils.WithLoginMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(value = CinemaScheduleController.class,includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)})
 class CinemaScheduleControllerTest {

    @MockBean
    private CinemaScheduleService cinemaScheduleService;

    @MockBean
    private CustomOAuth2Service oAuth2Service;

    @MockBean
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @MockBean
    private OAuth2FailHandler oAuth2FailHandler;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CinemaScheduleCreateRequestDTO scheduleRequestDTO;
    private CinemaScheduleCreateResponseDTO scheduleResponseDTO;

    private CinemaScheduleListResponseDTO schedule1;
    private CinemaScheduleListResponseDTO schedule2;
    private List<CinemaScheduleListResponseDTO> cinemaScheduleListResponseDTOList=new ArrayList<>();
    private Map<String,List<CinemaScheduleListResponseDTO>> groupByMovie=new HashMap<>();

    private CinemaScheduleResponseDTO cinemaScheduleResponseDTO;


    @BeforeEach
    void beforeEach(){

       scheduleRequestDTO = CinemaScheduleCreateRequestDTO.builder()
               .price(2000)
               .limitCount(20)
               .build();

       scheduleResponseDTO = CinemaScheduleCreateResponseDTO.builder()
               .cinemaScheduleId(1L)
               .price(2000)
               .limitCount(20)
               .build();

       schedule1= CinemaScheduleListResponseDTO.builder()
               .cinemaScheduleId(1L)
               .title("스타워즈")
               .headCount(5)
               .limitCount(20)
               .build();


       schedule2= CinemaScheduleListResponseDTO.builder()
               .cinemaScheduleId(2L)
               .title("스타워즈")
               .headCount(3)
               .limitCount(17)
               .build();


       cinemaScheduleListResponseDTOList.add(schedule1);
       cinemaScheduleListResponseDTOList.add(schedule2);

       groupByMovie.put("인디아나존스",cinemaScheduleListResponseDTOList);

       cinemaScheduleResponseDTO = CinemaScheduleResponseDTO.builder()
               .cinemaScheduleId(1L)
               .price(2000)
               .cinemaScheduleStatus(CinemaScheduleStatus.OPEN)
               .build();

    }


    @WithLoginMember
    @Test
    @DisplayName("영화 스케줄을 생성합니다")
    void createSchedule() throws Exception{

       given(cinemaScheduleService.createCinemaSchedule(anyLong(),anyLong(),any()))
               .willReturn(scheduleResponseDTO);


       mvc.perform(post("/api/schedule/create/1/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(scheduleRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.cinemaScheduleId").value(scheduleResponseDTO.getCinemaScheduleId()))
               .andExpect(jsonPath("$.price").value(scheduleResponseDTO.getPrice()))
               .andExpect(jsonPath("$.limitCount").value(scheduleResponseDTO.getLimitCount()));
    }

    @WithLoginMember
    @Test
    @DisplayName("영화 스케줄 리스트를 반환합니다")
    void getScheduleList() throws Exception{


       given(cinemaScheduleService.getCinemaScheduleList(anyLong())).willReturn(groupByMovie);

       mvc.perform(get("/api/schedule/getCinemaSchedule/1")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @WithLoginMember
    @Test
    @DisplayName("영화 스케줄 단건을 반환합니다")
    void getSchedule() throws Exception {
       given(cinemaScheduleService.getCinemaSchedule(anyLong())).willReturn(cinemaScheduleResponseDTO);

       mvc.perform(get("/api/schedule/getCinemaSchedule/1")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.cinemaScheduleId").value(cinemaScheduleResponseDTO.getCinemaScheduleId()))
               .andExpect(jsonPath("$.cinemaScheduleStatus").value(cinemaScheduleResponseDTO.getCinemaScheduleStatus().name()));
    }






}

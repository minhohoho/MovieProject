package com.example.movieproject.controller;

import com.example.movieproject.common.jwt.JwtProvider;
import com.example.movieproject.common.jwt.handler.JwtAccessDeniedHandler;
import com.example.movieproject.common.jwt.handler.JwtAuthenticationEntryPoint;
import com.example.movieproject.common.oauth2.handler.OAuth2FailHandler;
import com.example.movieproject.common.oauth2.handler.OAuth2SuccessHandler;
import com.example.movieproject.config.SecurityConfig;
import com.example.movieproject.dto.response.MyApplicationListResponseDTO;
import com.example.movieproject.service.ApplicationService;
import com.example.movieproject.service.CustomOAuth2Service;
import com.example.movieproject.testUtils.WithLoginMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = ApplicationController.class,includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)})
class ApplicationControllerTest {

    @MockBean
    private ApplicationService applicationService;

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

    private List<MyApplicationListResponseDTO> responseDTOList = new ArrayList<>();
    private MyApplicationListResponseDTO responseDTO;

    @BeforeEach
    void beforeEach(){

        responseDTO = MyApplicationListResponseDTO.builder()
                .applicationId(1L)
                .price(2000)
                .build();

        responseDTOList.add(responseDTO);
    }


    @WithLoginMember
    @Test
    @DisplayName("영화관 신청")
    void createApplication() throws Exception {

        given(applicationService.createApply(anyLong(),anyLong(),anyLong())).willReturn(true);

        mvc.perform(post("/api/application/create/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithLoginMember
    @Test
    @DisplayName("영화관 취소")
    void deleteApplication() throws Exception{

        given(applicationService.deleteApply(anyLong(),anyLong())).willReturn(true);

        mvc.perform(delete("/api/application/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}

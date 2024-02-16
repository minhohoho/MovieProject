package com.example.movieproject.controller;

import com.example.movieproject.common.jwt.JwtAuthenticationFilter;
import com.example.movieproject.config.SecurityConfig;
import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Review;
import com.example.movieproject.dto.request.ReviewCreateRequestDTO;
import com.example.movieproject.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value=ReviewController.class,excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {
                SecurityConfig.class,
                JwtAuthenticationFilter.class
        })
})
@DisplayName("리뷰 컨트롤러 테스트")
@WithMockUser(username = "석민호",roles = "USER")
public class ReviewControllerTest {


    @MockBean
    private MovieService movieService;

    @Autowired private MockMvc mockMvc;

    private Member member;
    private Review review;
    private ReviewCreateRequestDTO ReviewRequestDTO;

    @BeforeEach
    public void beforeEach(){

    }

    @DisplayName("[리뷰 등록]-정상")
    @Test
    void given_when_then(){

    }

    @DisplayName("[리뷰 등록]-멤버 없음")
    @Test
    void given_when_then1(){

    }

    @DisplayName("[리뷰 등록]-평점의 범위 잘못됨")
    @Test
    void given_when_then2(){

    }

    @DisplayName("[리뷰 단건 조회]-정상")
    @Test
    void given_when_then3(){

    }

    @DisplayName("[리뷰 단건 조회]-리뷰 아이디 없음")
    @Test
    void given_when_then4(){

    }

    @DisplayName("[리뷰 전체 조회]-영화 id 없음")
    @Test
    void given_when_then5(){

    }





}

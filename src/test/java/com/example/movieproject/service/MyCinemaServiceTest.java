package com.example.movieproject.service;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.kakaoApi.DocumentDTO;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.kakaoApi.MetaDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.request.MyCinemaUpdateRequestDTO;
import com.example.movieproject.dto.response.MyCinemaCreateResponseDTO;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.MyCinemaException;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MyCinemaServiceTest {

    @InjectMocks
    private MyCinemaService myCinemaService;
    @Mock
    private MyCinemaRepository myCinemaRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CinemaScheduleRepository cinemaScheduleRepository;

    @DisplayName("[영화관 생성]-성공")
    @Test
    void createMyCinema(){

        //given
        Member member = Member.builder()
                .memberId(1L)
                .build();

        MyCinema myCinema = MyCinema.builder()
                .myCinemaId(1L)
                .member(member)
                .cinemaItem("test")
                .latitude(12.12)
                .longitude(12.12)
                .build();

        MyCinemaCreateRequestDTO requestDTO = MyCinemaCreateRequestDTO.builder()
                .cinemaItem("test")
                .build();

        MetaDTO metaDTO = new MetaDTO(1);
        DocumentDTO documentDTO = new DocumentDTO("서울시 도봉구",12.12,12.12);
        KakakoApiResponseDTO kakakoApiResponseDTO = new KakakoApiResponseDTO(metaDTO, List.of(documentDTO));



        given(memberRepository.findById(member.getMemberId()))
                .willReturn(Optional.of(member));

        given(myCinemaRepository.save(any())).willReturn(myCinema);


        //when
        MyCinemaCreateResponseDTO responseDTO = myCinemaService.createMyCinema(1L,requestDTO,kakakoApiResponseDTO);

        //then
        assertEquals(12.12,responseDTO.getLongitude());
        assertEquals(12.12,responseDTO.getLatitude());


    }

    @DisplayName("[영화관 생성]-사용자가 존재하지 않음")
    @Test
    void failByNotExistMemberCreateMyCinema(){

        //given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        MyCinemaCreateRequestDTO requestDTO = MyCinemaCreateRequestDTO.builder()
                .cinemaItem("test")
                .build();

        MetaDTO metaDTO = new MetaDTO(1);
        DocumentDTO documentDTO = new DocumentDTO("서울시 도봉구",12.12,12.12);
        KakakoApiResponseDTO kakakoApiResponseDTO = new KakakoApiResponseDTO(metaDTO, List.of(documentDTO));

        MyCinemaException myCinemaException = assertThrows(MyCinemaException.class,
                ()->myCinemaService.createMyCinema(anyLong(),requestDTO,kakakoApiResponseDTO));


        assertEquals(ErrorList.NOT_EXIST_MEMBER.getErrorMessage(),myCinemaException.getErrorMessage());
    }

    @DisplayName("[영화관-수정]-실패 권한 없음")
    @Test
    void failByAuthPutMyCinema(){

        //given
        MyCinemaUpdateRequestDTO myCinemaUpdateRequestDTO = MyCinemaUpdateRequestDTO.builder().build();

        Member member = Member.builder()
                .memberId(1L)
                .build();

        MyCinema myCinema = MyCinema.builder()
                .myCinemaId(1L)
                .member(member)
                .build();

        given(myCinemaRepository.findMyCinemaInfo(anyLong())).willReturn(myCinema);



        //when
        MyCinemaException myCinemaException = assertThrows(MyCinemaException.class,
                ()->myCinemaService.updateMyCinemaInfo(1L,2L,myCinemaUpdateRequestDTO,new KakakoApiResponseDTO()));


        //then
        assertEquals(myCinemaException.getErrorMessage(),ErrorList.NOT_MATCH_MY_CINEMA.getErrorMessage());
    }




}

package com.example.movieproject.service;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.response.MyCinemaCreateResponseDTO;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MovieRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyCinemaService {

    private final MyCinemaRepository myCinemaRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MyCinemaCreateResponseDTO createMyCinema(Long memberId, MyCinemaCreateRequestDTO requestDTO, KakakoApiResponseDTO kakakoApiResponseDTO){

        Member member = memberRepository.findById(memberId).orElseThrow();

        MyCinema myCinema = MyCinemaCreateRequestDTO.dtoToEntity(member,requestDTO,kakakoApiResponseDTO);

        return MyCinemaCreateResponseDTO.entityToDTO(myCinemaRepository.save(myCinema));
    }



}

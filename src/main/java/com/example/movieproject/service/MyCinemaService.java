package com.example.movieproject.service;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.request.MyCinemaUpdateRequestDTO;
import com.example.movieproject.dto.response.*;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyCinemaService {

    private final MyCinemaRepository myCinemaRepository;
    private final MemberRepository memberRepository;
    private final CinemaScheduleRepository cinemaScheduleRepository;

    @Transactional
    public MyCinemaCreateResponseDTO createMyCinema(Long memberId, MyCinemaCreateRequestDTO requestDTO, KakakoApiResponseDTO kakakoApiResponseDTO){

        Member member = memberRepository.findById(memberId).orElseThrow();

        MyCinema myCinema = MyCinemaCreateRequestDTO.dtoToEntity(member,requestDTO,kakakoApiResponseDTO);

        return MyCinemaCreateResponseDTO.entityToDTO(myCinemaRepository.save(myCinema));
    }

    @Transactional
    public void updateMyCinemaInfo(Long myCinemaId, Long memberId, MyCinemaUpdateRequestDTO updateDTO,KakakoApiResponseDTO kakakoApiResponseDTO){

        MyCinema myCinema = myCinemaRepository.findMyCinemaInfo(myCinemaId);

        validate(myCinema,memberId);

        myCinema.updateMyCinema(updateDTO,kakakoApiResponseDTO);

    }

    @Transactional
    public void deleteMyCinema(Long myCinemaId,Long memberId){

        MyCinema myCinema = myCinemaRepository.findMyCinemaInfo(myCinemaId);

        validate(myCinema,memberId);

        cinemaScheduleRepository.deleteAllByMyCinema(myCinema);

        myCinemaRepository.deleteById(myCinema.getMyCinemaId());

    }

    @Transactional(readOnly = true)
    public List<MyCinemaListResponseDTO> getMyCinemaList(Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow();

        return myCinemaRepository.findByMember(member).stream().map(MyCinemaListResponseDTO::entityToDTO).toList();
    }

    @Transactional(readOnly = true)
    public MyCinemaResponseDTO getMyCinema(Long memberId,Long myCinemaId){

         MyCinema myCinema = myCinemaRepository.findMyCinemaInfo(myCinemaId);

         validate(myCinema,memberId);

        return MyCinemaResponseDTO.entityToDTO(myCinema);
    }

    @Transactional(readOnly = true)
    public Page<CinemaListResponseDTO> getCinemaList(Pageable pageable){


        return  myCinemaRepository.findAll(pageable).map(CinemaListResponseDTO::entityToDTO);
    }

   @Transactional(readOnly = true)
   public CinemaResponseDTO getCinema(Long myCinemaId){

        MyCinema myCinema = myCinemaRepository.findMyCinemaInfo(myCinemaId);

        String name = myCinema.getMember().getName();

        return CinemaResponseDTO.entityToDTO(myCinema,name);
   }





    private void validate(MyCinema myCinema,Long memberId){
        if(!Objects.equals(myCinema.getMember().getMemberId(),memberId)){
            throw new RuntimeException();
        }
    }





}

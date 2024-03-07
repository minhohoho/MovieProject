package com.example.movieproject.service;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.MyCinema;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.request.MyCinemaUpdateRequestDTO;
import com.example.movieproject.dto.response.*;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.MyCinemaException;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MemberRepository;
import com.example.movieproject.repository.MyCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCinemaService {

    private final MyCinemaRepository myCinemaRepository;
    private final MemberRepository memberRepository;
    private final CinemaScheduleRepository cinemaScheduleRepository;
    private final RedisTemplate<String,String> redisTemplate;

    private static final int MAX_SEARCH_COUNT = 3; // 최대 검색 갯수
    private static final double RADIUS_KM = 10.0; // 반경 10 km
    private static final String RANKING = "MyCinemaRANKING";

    @Transactional
    public MyCinemaCreateResponseDTO createMyCinema(Long memberId, MyCinemaCreateRequestDTO requestDTO, KakakoApiResponseDTO kakakoApiResponseDTO){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MyCinemaException(ErrorList.NOT_EXIST_MEMBER));

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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MyCinemaException(ErrorList.NOT_EXIST_MEMBER));


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

        String cinemaName = myCinema.getCinemaName();

       try {
           redisTemplate.opsForZSet().incrementScore(RANKING, cinemaName, 1);
       } catch (Exception e) {
           e.printStackTrace();
       }

       return CinemaResponseDTO.entityToDTO(myCinema,name);
   }

   @Transactional(readOnly = true)
   public List<CinemaRakingResponseDTO> getCinemaRanking(){

       Set<ZSetOperations.TypedTuple<String>> result = redisTemplate.opsForZSet().reverseRangeWithScores(RANKING, 0, 9);


       return  result.stream().map(CinemaRakingResponseDTO::entityToDTO).collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public List<MyCinemaListResponseDTO> getCinemaByDistance(KakakoApiResponseDTO kakakoApiResponseDTO){

        List<MyCinema> myCinemaList = myCinemaRepository.findAll();

        double MyLatitude = kakakoApiResponseDTO.getDocumentList().get(0).getLatitude();

        double MyLongitude = kakakoApiResponseDTO.getDocumentList().get(0).getLongitude();

        return myCinemaList.stream().map(myCinema -> MyCinemaListResponseDTO
                .builder()
                        .myCinemaId(myCinema.getMyCinemaId())
                        .cinemaName(myCinema.getCinemaName())
                        .addressName(myCinema.getAddressName())
                        .distance(calculateDistance(MyLatitude,MyLongitude, myCinema.getLatitude(), myCinema.getLongitude()))
                .build())
                        .filter(myCinemaListResponseDTO -> myCinemaListResponseDTO.getDistance()<=RADIUS_KM)
                        .sorted(Comparator.comparing(MyCinemaListResponseDTO::getDistance))
                        .limit(MAX_SEARCH_COUNT)
                        .collect(Collectors.toList());
   }

   private double calculateDistance(double lat1,double lon1,double lat2,double lon2){
        lat1= Math.toRadians(lat1);
        lon1= Math.toRadians(lon1);
        lat2= Math.toRadians(lat2);
        lon2= Math.toRadians(lon2);

       double earthRadius = 6371; //Kilometers

        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
   }



    private void validate(MyCinema myCinema,Long memberId){
        if(!Objects.equals(myCinema.getMember().getMemberId(),memberId)){
            throw new MyCinemaException(ErrorList.NOT_MATCH_MY_CINEMA);
        }
    }





}

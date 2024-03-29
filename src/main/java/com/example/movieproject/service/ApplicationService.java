package com.example.movieproject.service;

import com.example.movieproject.common.type.AlertType;
import com.example.movieproject.common.type.CinemaScheduleStatus;
import com.example.movieproject.domain.*;
import com.example.movieproject.dto.response.MyApplicationListResponseDTO;
import com.example.movieproject.exceptionHandle.ApplicationException;
import com.example.movieproject.exceptionHandle.ErrorList;
import com.example.movieproject.exceptionHandle.ReviewException;
import com.example.movieproject.repository.ApplicationRepository;
import com.example.movieproject.repository.CinemaScheduleRepository;
import com.example.movieproject.repository.MemberRepository;

import com.example.movieproject.repository.MyCinemaRepository;
import com.example.movieproject.repository.alert.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CinemaScheduleRepository cinemaScheduleRepository;
    private final MemberRepository memberRepository;
    private final MyCinemaRepository myCinemaRepository;
    private final AlertRepository alertRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Boolean createApply(Long cinemaScheduleId,Long memberId,Long myCinemaId){

        CinemaSchedule cinemaSchedule = cinemaScheduleRepository.findById(cinemaScheduleId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_CINEMA_SCHEDULE));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ReviewException(ErrorList.NOT_EXIST_MEMBER));


        if(cinemaSchedule.getCinemaScheduleStatus()== CinemaScheduleStatus.CLOSED){
            throw new ApplicationException(ErrorList.CANT_APPLY);
        }

       Integer headCount = cinemaSchedule.getHeadCount();
       headCount ++;

       cinemaSchedule.addHeadCount(headCount);
       if(headCount.equals(cinemaSchedule.getLimitCount())) cinemaSchedule.changeStatus(CinemaScheduleStatus.CLOSED);

       Application application = Application.builder()
                .member(member)
                .cinemaSchedule(cinemaSchedule)
                .build();

        applicationRepository.save(application);


        // 알림 -> 내 영화관을 만든 사람에게 신청 알림
        MyCinema myCinema = myCinemaRepository.findMyCinemaInfo(myCinemaId);
        Member cinemaPer = memberRepository.findById(myCinema.getMember().getMemberId()).orElseThrow();

        Alert savedAlert = Alert.builder()
                .member(cinemaPer)
                .sender(member.getName()) // 당신의 영화관에 "아무개"가 신청하였습니다를 알려주기 위해서
                .isRead(false)
                .alertType(AlertType.NEW_APPLICATION)
                .build();
        alertRepository.save(savedAlert);

        eventPublisher.publishEvent(savedAlert);
        return true;
    }


    @Transactional
    public Boolean deleteApply(Long applicationId,Long memberId){

         // 예약 취소 로직
         Application application = applicationRepository.findByApplication(applicationId);


         validate(application,memberId);


         applicationRepository.delete(application);


         // 예약 목록에서 데이터 변경
         CinemaSchedule cinemaSchedule = cinemaScheduleRepository.findById(application.getCinemaSchedule().getCinemaScheduleId())
                 .orElseThrow(()->new ApplicationException(ErrorList.NOT_EXIST_CINEMA_SCHEDULE));


         if(cinemaSchedule.getCinemaScheduleStatus().equals(CinemaScheduleStatus.CLOSED)){
             cinemaSchedule.changeStatus(CinemaScheduleStatus.OPEN);
         }
        Integer headCount = cinemaSchedule.getHeadCount();
        headCount--;
        cinemaSchedule.addHeadCount(headCount);

        return true;
    }

    @Transactional(readOnly = true)
    public List<MyApplicationListResponseDTO> getMyApplicationList(Long memberId, LocalDateTime startDate,LocalDateTime endDate){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new ApplicationException(ErrorList.NOT_EXIST_MEMBER));


        return applicationRepository.findMyApplicationList(member,startDate,endDate).stream().map(MyApplicationListResponseDTO::entityToDTO).toList();
    }


    private void validate(Application application,Long memberId){

        if(!Objects.equals(application.getMember().getMemberId(),memberId)){
            throw new ApplicationException(ErrorList.NOT_MATCH_APPLICATION);
        }

    }




}

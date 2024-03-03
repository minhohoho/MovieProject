package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.response.CinemaScheduleListResponseDTO;
import com.example.movieproject.dto.response.MyApplicationListResponseDTO;
import com.example.movieproject.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @ApiOperation(value = "영화관 예매 api",notes = "영화관의 인원의 유효성 검사를 하고 유효하다면 +1하고 데이터베이스에 기록한다")
    @PostMapping("/create/{cinemaScheduleId}")
    public ResponseEntity<Boolean> createApply(
            @PathVariable Long cinemaScheduleId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok(applicationService.createApply(cinemaScheduleId,memberId));
    }
    @ApiOperation(value = "영화관 예매 취소 api",notes = "영화관 예매를 취소한다 유효성 검사후 -1을 반영시킨다")
    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<Boolean> deleteApply(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long applicationId
    ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok(applicationService.deleteApply(applicationId,memberId));
    }

    @ApiOperation(value = "예매 리스트 조회 api",notes = "회원은 자신이 예매한 스케쥴을 조회할 수 있다")
    @GetMapping("/getMyApplicationList")
    public ResponseEntity<List<MyApplicationListResponseDTO>> getMyApplicationList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate

    ){
        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(applicationService.getMyApplicationList(memberId,startDate,endDate));
    }





}

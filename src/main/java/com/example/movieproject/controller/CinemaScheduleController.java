package com.example.movieproject.controller;

import com.example.movieproject.common.type.Income;
import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.request.CinemaScheduleUpdateRequestDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleListResponseDTO;
import com.example.movieproject.dto.response.CinemaScheduleResponseDTO;
import com.example.movieproject.dto.response.YearIncomeResponseDTO;
import com.example.movieproject.service.CinemaScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CinemaScheduleController {

    private final CinemaScheduleService cinemaScheduleService;
    @ApiOperation(value = "영화관 스케줄 생성 api",notes = "영화관을 생성한 회원은 스케줄을 등록할 수 있다")
    @PostMapping("/create/{myCinemaId}/{movieId}")
    public ResponseEntity<CinemaScheduleCreateResponseDTO> createCinemaSchedule(
            @PathVariable Long myCinemaId,
            @PathVariable Long movieId,
            @RequestBody CinemaScheduleCreateRequestDTO requestDTO
            ){
        return ResponseEntity.ok().body(cinemaScheduleService.createCinemaSchedule(myCinemaId,movieId,requestDTO));
    }

    @ApiOperation(value = "영화관 스케줄 리스트 조회 api",notes = "해당 영화관의 영화 스케줄을 확인할 수 있다")
    @GetMapping("/getCinemaScheduleList/{myCinemaId}")
    public ResponseEntity<Map<String,List<CinemaScheduleListResponseDTO>>> getCinemaScheduleList(
            @PathVariable Long myCinemaId){

        return ResponseEntity.ok().body(cinemaScheduleService.getCinemaScheduleList(myCinemaId));
    }

    @ApiOperation(value = "영화관 스케줄 조회 api",notes = "스케줄에 대한 자세한 정보를 확인 할 수 있다")
    @GetMapping("/getCinemaSchedule/{cinemaScheduleId}")
    public ResponseEntity<CinemaScheduleResponseDTO> getCinemaSchedule(
            @PathVariable Long cinemaScheduleId
    ){
        return ResponseEntity.ok().body(cinemaScheduleService.getCinemaSchedule(cinemaScheduleId));
    }

    @ApiOperation(value = "영화관 스케줄 수정 api",notes = "권한 인증이 된 회원이라면 영화관 스케줄을 수정할 수 있다")
    @PutMapping("/update/{cinemaScheduleId}")
    public ResponseEntity<Boolean> updateCinemaSchedule(
            @PathVariable Long cinemaScheduleId,
            @RequestBody CinemaScheduleUpdateRequestDTO updateDTO
            ){

        return ResponseEntity.ok().body(cinemaScheduleService.updateCinemaSchedule(cinemaScheduleId,updateDTO));
    }

    @ApiOperation(value = "영화관 스케줄 삭제 api",notes = "권한 인증이 된 회원이라면 영화관 스케줄을 삭제할 수 있다")
    @DeleteMapping("/delete/{{cinemaScheduleId}}")
    public ResponseEntity<Boolean> deleteCinemaSchedule(
            @PathVariable Long cinemaScheduleId){

        return ResponseEntity.ok().body(cinemaScheduleService.deleteCinemaSchedule(cinemaScheduleId));
    }

    @ApiOperation(value = "영화관 수입 조회 api",notes = "권한 인증이 된 회원이라면 수입을 타입별로 조회할 수 있다")
    @GetMapping("/getMyCinemaIncome/{myCinemaId}")
    public ResponseEntity<List<YearIncomeResponseDTO>> getMyCinemaIncome(
            @PathVariable Long myCinemaId,
            @RequestParam Income income){

        return ResponseEntity.ok().body(cinemaScheduleService.getYearIncome(myCinemaId,income));
    }

}

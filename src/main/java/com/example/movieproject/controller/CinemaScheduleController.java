package com.example.movieproject.controller;

import com.example.movieproject.dto.request.CinemaScheduleCreateRequestDTO;
import com.example.movieproject.dto.response.CinemaScheduleCreateResponseDTO;
import com.example.movieproject.service.CinemaScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CinemaScheduleController {

    private final CinemaScheduleService cinemaScheduleService;

    @ApiOperation(value = "영화관 스케쥴 등록 api",notes = "회원은 자신이 만든 영화관 리스트에서 단건 조회해서 스케쥴을 등록가능하다")
    @PostMapping("/create/{myCinemaId}/{movieId}")
    public ResponseEntity<CinemaScheduleCreateResponseDTO> createSchedule(
            @PathVariable Long myCinemaId,
            @PathVariable Long movieId,
            @RequestBody CinemaScheduleCreateRequestDTO requestDTO
    ){
        return ResponseEntity.ok().body(cinemaScheduleService.createSchedule(myCinemaId, movieId, requestDTO));
    }






}

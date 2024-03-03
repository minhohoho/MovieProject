package com.example.movieproject.controller;

import com.example.movieproject.common.type.Role;
import com.example.movieproject.domain.MovieStaff;
import com.example.movieproject.service.MovieStaffService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movieStaff")
public class MovieStaffController {

    private final MovieStaffService movieStaffService;

    @ApiOperation(value = "영화_스태프 정보 입력",notes = "관리자가 영화 정보 생성 후 누락된 정보가 있다면 추가 가능")
    @PostMapping("/create/{MovieId}/{StaffId}")
    public ResponseEntity<Boolean> createMovieStaffInfo(
            @PathVariable Long MovieId,
            @PathVariable Long StaffId,
            @RequestParam Role role
            ){
        return ResponseEntity.ok().body(movieStaffService.createMovieStaff(MovieId,StaffId,role));
    }

    @ApiOperation(value = "영화_스태프 정보 수정 api",notes = "영화_스태프의 정보가 잘못된 경우 수정하기 위한 메서드")
    @PutMapping("/update/{MovieStaffId}")
    public ResponseEntity<Boolean> updateMovieStaffInfo(
            @PathVariable Long MovieStaffId,
            @RequestParam Role role
    ){

        return ResponseEntity.ok().body(movieStaffService.updateMovieStaff(MovieStaffId,role));
    }

    @ApiOperation(value = "영화_스태프 정보 삭제 api",notes = "영화의 스태프가 아닌 경우 삭제하기 위한 메서드")
    @DeleteMapping("/delete/{MovieStaffId}")
    public ResponseEntity<Boolean> deleteMovieStaff(@PathVariable Long MovieStaffId){


        return ResponseEntity.ok().body(movieStaffService.deleteMovieStaff(MovieStaffId));
    }






}

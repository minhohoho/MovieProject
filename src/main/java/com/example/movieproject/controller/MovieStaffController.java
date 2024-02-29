package com.example.movieproject.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movieStaff")
public class MovieStaffController {

    @ApiOperation(value = "영화_스태프 정보 입력",notes = "관리자가 영화 정보 생성 후 누락된 정보가 있다면 추가 가능")
    @PostMapping("/create")
    public ResponseEntity<Void> createMovieStaffInfo(){
        return null;
    }

    @ApiOperation(value = "영화_스태프 정보 수정 api",notes = "영화_스태프의 정보가 잘못된 경우 수정하기 위한 메서드")
    @PutMapping("/update")
    public ResponseEntity<Void> updateMovieStaffInfo(){
        return null;
    }

    @ApiOperation(value = "영화_스태프 정보 삭제 api",notes = "영화의 스태프가 아닌 경우 삭제하기 위한 메서드")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMovieStaff(){
        return null;
    }






}

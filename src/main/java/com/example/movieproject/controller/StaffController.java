package com.example.movieproject.controller;

import com.example.movieproject.dto.request.StaffCreateRequestDTO;
import com.example.movieproject.service.StaffService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @ApiOperation(value="스태프 등록 api",notes = "배우 및 감독의 개인정보를 저장하기 위한 엔티티이다")
    @PostMapping("/create")
    //@Secured("")
    public ResponseEntity<Long> createStaff(
            @RequestBody StaffCreateRequestDTO requestDTO
            ){

        return ResponseEntity.ok(staffService.createStaff(requestDTO));
    }



}

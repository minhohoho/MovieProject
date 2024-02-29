package com.example.movieproject.controller;

import com.example.movieproject.dto.request.StaffCreateRequestDTO;
import com.example.movieproject.dto.request.StaffUpdateRequestDTO;
import com.example.movieproject.dto.response.StaffUpdateResponseDTO;
import com.example.movieproject.service.StaffService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value="스태프 정보 수정 api",notes = "스태프의 정보를 수정한다")
    @PutMapping("/update/{staffId}")
    public ResponseEntity<StaffUpdateResponseDTO> updateStaffInfo(
            @PathVariable Long staffId,
            @RequestBody StaffUpdateRequestDTO updateDTO
            ){
        return ResponseEntity.ok().body(staffService.updateStaffInfo(staffId,updateDTO));
    }

    @ApiOperation(value="스태프 정보 삭제 api",notes = "스태프 한명의 모든 정보를 삭제하고 영화_스태프 엔티티에 스태프 FK가 있다면 삭제한다")
    @DeleteMapping("/delete/{staffId}")
    public ResponseEntity<Void> deleteStaff(
            @PathVariable Long staffId
    ){
        staffService.deleteStaff(staffId);

        return ResponseEntity.ok().build();
    }




}

package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.response.MyCinemaCreateResponseDTO;
import com.example.movieproject.service.KakaoAddressSearchService;
import com.example.movieproject.service.MyCinemaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myCinema")
@Slf4j
public class MyCinemaController {

    private final MyCinemaService myCinemaService;
    private final KakaoAddressSearchService kakaoAddressSearchService;

    @ApiOperation(value="영화관 생성 api",notes = "로그인이 된 사용자라면 수익 창출을 위한 자신만의 영화관을 생성 가능합니다")
    @PostMapping("/create")
    public ResponseEntity<MyCinemaCreateResponseDTO> createMyCinema(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody MyCinemaCreateRequestDTO requestDTO
            ){
        Long memberId = userPrincipal.getId();

        log.info(memberId.toString());

        KakakoApiResponseDTO kakakoApiResponseDTO = kakaoAddressSearchService.getAddress(requestDTO.getAddressName());

        return ResponseEntity.ok().body(myCinemaService.createMyCinema(memberId,requestDTO,kakakoApiResponseDTO));
    }










    @ApiOperation(value = "영화관 정보 수정 api",notes = "권한 인증이 된 사용자라면 영화관 정보를 수정 가능합니다")
    @PutMapping("/update")
    public ResponseEntity<Void> updateMyCinemaInfo(){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "영화관 정보 삭제 api",notes = "권한 인증이 된 사용자라면 영화관을 삭제 가능합니다")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMyCinema(){
        return ResponseEntity.ok().build();
    }




}

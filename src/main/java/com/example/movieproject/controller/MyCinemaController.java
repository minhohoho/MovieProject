package com.example.movieproject.controller;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.dto.kakaoApi.KakakoApiResponseDTO;
import com.example.movieproject.dto.request.MyCinemaCreateRequestDTO;
import com.example.movieproject.dto.request.MyCinemaUpdateRequestDTO;
import com.example.movieproject.dto.response.*;
import com.example.movieproject.service.KakaoAddressSearchService;
import com.example.movieproject.service.MyCinemaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myCinema")
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


        KakakoApiResponseDTO kakakoApiResponseDTO = kakaoAddressSearchService.getAddress(requestDTO.getAddressName());

        return ResponseEntity.ok().body(myCinemaService.createMyCinema(memberId,requestDTO,kakakoApiResponseDTO));
    }

    @ApiOperation(value = "영화관 이미지 추가 api",notes = "회원은 자신의 영화관에 이미지를 추가할 수 있다")
    @PostMapping("/upload/{myCinemaId}")
    public ResponseEntity<String> uploadImage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long myCinemaId,
            @RequestBody MultipartFile multipartFile
            ){

        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(myCinemaService.uploadImage(memberId,myCinemaId,multipartFile));
    }


    @ApiOperation(value = "영화관 정보 수정 api",notes = "권한 인증이 된 사용자라면 영화관 정보를 수정 가능합니다")
    @PutMapping("/update/{myCinemaId}")
    public ResponseEntity<Void> updateMyCinemaInfo(
            @PathVariable Long myCinemaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody MyCinemaUpdateRequestDTO updateDTO
    ){

        Long memberId = userPrincipal.getId();

        KakakoApiResponseDTO kakakoApiResponseDTO = kakaoAddressSearchService.getAddress(updateDTO.getAddressName());

        myCinemaService.updateMyCinemaInfo(myCinemaId,memberId,updateDTO,kakakoApiResponseDTO);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "영화관 정보 삭제 api",notes = "권한 인증이 된 사용자라면 영화관을 삭제 가능합니다")
    @DeleteMapping("/delete/{myCinemaId}")
    public ResponseEntity<Void> deleteMyCinema(
            @PathVariable Long myCinemaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){

        Long memberId = userPrincipal.getId();

        myCinemaService.deleteMyCinema(myCinemaId,memberId);

        return ResponseEntity.ok().build();
    }
    @ApiOperation(value="회원의 영화관 List api",notes = "권한 인증이 된 회원이라면 회원의 영화관 리스트를 조회할 수 있습니다")
    @GetMapping("/getMyCinemaList")
    public ResponseEntity<List<MyCinemaListResponseDTO>> getMyCinemaList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){

        Long memberId = userPrincipal.getId();


        return ResponseEntity.ok().body(myCinemaService.getMyCinemaList(memberId));
    }

    @ApiOperation(value = "회원의 영화관 단건 조회 api",notes = "인증이 된 회원이라면 자신의 영화관을 단건 조회할 수 있습니다")
    @GetMapping("/getMyCinema/{myCinemaId}")
    public ResponseEntity<MyCinemaResponseDTO> getMyCinema(
            @PathVariable Long myCinemaId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long memberId = userPrincipal.getId();

        return ResponseEntity.ok().body(myCinemaService.getMyCinema(memberId,myCinemaId));
    }

    @ApiOperation(value = "영화관 페이징 api",notes = "영화관 리스트를 페이징해서 보여줍니다")
    @GetMapping("/getCinemaList")
    public ResponseEntity<Page<CinemaListResponseDTO>> getCinemaList(
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ){

        return ResponseEntity.ok().body( myCinemaService.getCinemaList(pageable));
    }

    @ApiOperation(value = "영화관 단건 조회 api",notes = "영화관에 대한 정보를 원하거나 예매를 원한다면 정보를 제공합니다")
    @GetMapping("/getCinema/{myCinemaId}")
    public ResponseEntity<CinemaResponseDTO> getCinema(
            @PathVariable Long myCinemaId){

        return ResponseEntity.ok().body(myCinemaService.getCinema(myCinemaId));
    }

    @ApiOperation(value = "영화관 실시간 순위 조회 api",notes = "현재 조회된 순")
    @GetMapping("/getCinemaRanking")
    public ResponseEntity<List<CinemaRakingResponseDTO>> getCinemaRanking(){

        return ResponseEntity.ok().body(myCinemaService.getCinemaRanking());
    }

    @ApiOperation(value = "")
    @GetMapping("/getCinemaByDistance")
    public ResponseEntity<List<MyCinemaListResponseDTO>> getCinemaByDistance(
            @RequestParam String addressName){

        KakakoApiResponseDTO kakakoApiResponseDTO = kakaoAddressSearchService.getAddress(addressName);

        return ResponseEntity.ok().body(myCinemaService.getCinemaByDistance(kakakoApiResponseDTO));
    }



}

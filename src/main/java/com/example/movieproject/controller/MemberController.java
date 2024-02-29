package com.example.movieproject.controller;

import com.example.movieproject.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value="토큰 재발급",notes = "사용자는 토큰을 재발급 받을 수 있습니다")
    @GetMapping("/reissue")
    public ResponseEntity<Boolean> reissueToken(@RequestParam String refreshToken){

        return ResponseEntity.ok().body(memberService.reissueToken(refreshToken));
    }

    @ApiOperation(value = "회원 정보 수정 api",notes = "로그인 한 회원이라면 자신의 정보를 수정 가능하다")
    @PutMapping("/update/")
    public ResponseEntity<Void> updateMemberInfo(){
        return ResponseEntity.ok().build();
    }


}

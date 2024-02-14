package com.example.movieproject.controller;

import com.example.movieproject.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}

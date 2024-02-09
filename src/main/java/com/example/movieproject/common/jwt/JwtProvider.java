package com.example.movieproject.common.jwt;

import com.example.movieproject.dto.response.MemberResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    public MemberResponseDTO generateAccessToken(){
        return MemberResponseDTO.builder()
                .accessToken("임시 jwt 토큰")
                .build();
    }


}

package com.example.movieproject.service;

import com.example.movieproject.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

     private final JwtProvider jwtProvider;

     @Transactional
     public Boolean reissueToken(String refreshToken){
         return jwtProvider.reissueToken(refreshToken);
     }


}

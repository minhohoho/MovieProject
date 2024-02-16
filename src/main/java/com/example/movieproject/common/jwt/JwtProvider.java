package com.example.movieproject.common.jwt;

import com.example.movieproject.common.oauth2.info.UserPrincipal;
import com.example.movieproject.common.type.UserRole;
import com.example.movieproject.dto.response.TokenResponseDTO;
import com.example.movieproject.repository.RedisTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider implements InitializingBean {

        private static final String AUTHORITIES_KEY="ROLE";
        private static final String MEMBER_ID_KEY ="MEMBER_ID";

        private final String secretKey;
        private static Key signingKey;
        private final Long  accessTokenValidityInMilliseconds;
        private final Long refreshTokenValidityInMilliseconds;

        private final RedisTokenRepository redisTokenRepository;

        public JwtProvider(
                @Value("${jwt.secret}") String secretKey,
                @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
                @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds,
                RedisTokenRepository redisTokenRepository
        ){
            this.secretKey=secretKey;
            this.accessTokenValidityInMilliseconds=accessTokenValidityInMilliseconds;
            this.refreshTokenValidityInMilliseconds=refreshTokenValidityInMilliseconds;
            this.redisTokenRepository=redisTokenRepository;
        }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public TokenResponseDTO createToken(Long memberId, UserRole role){
        Date now = new Date();


            String accessToken = Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","HS512")
                    .setExpiration(new Date(now.getTime()+accessTokenValidityInMilliseconds))
                    .setSubject("access-token")
                    .claim(MEMBER_ID_KEY,memberId)
                    .claim(AUTHORITIES_KEY,role)
                    .signWith(signingKey,SignatureAlgorithm.HS512)
                    .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS512")
                .setExpiration(new Date(now.getTime()+refreshTokenValidityInMilliseconds))
                .setSubject("refresh-token")
                .claim(MEMBER_ID_KEY,memberId)
                .signWith(signingKey,SignatureAlgorithm.HS512)
                .compact();
        redisTokenRepository.save(memberId,refreshToken,refreshTokenValidityInMilliseconds);



        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

       public Authentication getAuthentication(String token){
           Long memberId = Long.parseLong(getClaims(token).get(MEMBER_ID_KEY).toString());
           UserRole role = UserRole.valueOf(getClaims(token).get(AUTHORITIES_KEY).toString());

           UserPrincipal userPrincipal = UserPrincipal.save(memberId,role);

           return new UsernamePasswordAuthenticationToken(userPrincipal,"",userPrincipal.getAuthorities());
       }

       public Claims getClaims(String token){
            try{
                return Jwts.parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            }catch (ExpiredJwtException e){
                return e.getClaims();
            }
       }

    public boolean validateToken(String token) {
            try{
                if(!StringUtils.hasText(token)){
                    return false;
                }
                return  getClaims(token).getExpiration().after(new Date());
          }catch(Exception e){
              return false;
          }
    }

    public Boolean reissueToken(String refreshToken){
        Long now = System.currentTimeMillis();
        Long memberId = Long.parseLong(getClaims(refreshToken).get(MEMBER_ID_KEY).toString());

        if(redisTokenRepository.isExists(memberId) && validateToken(refreshToken)){
            String reissueToken = Jwts.builder()
                    .setHeaderParam("typ","JWT")
                    .setHeaderParam("alg","HS512")
                    .setExpiration(new Date(now+refreshTokenValidityInMilliseconds))
                    .setSubject("refresh-token")
                    .claim(MEMBER_ID_KEY,memberId)
                    .signWith(signingKey,SignatureAlgorithm.HS512)
                    .compact();
            redisTokenRepository.save(memberId,reissueToken,refreshTokenValidityInMilliseconds);
            return  true;
        }
            return  false;
    }

}

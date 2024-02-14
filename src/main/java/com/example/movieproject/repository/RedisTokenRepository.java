package com.example.movieproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RedisTokenRepository {

    private final RedisTemplate<String,String> redisTemplate;

    private static final String KEY_PREFIX= "refreshToken::";

    public void save(Long memberId,String refreshToken,Long ExpiredMin){
        redisTemplate.opsForValue().set(KEY_PREFIX+memberId,refreshToken, Duration.ofMinutes(ExpiredMin));
    }
    public Boolean isExists(Long memberId){
        String token = redisTemplate.opsForValue().get(KEY_PREFIX + memberId);
        if(!StringUtils.hasText(token)){
            return false;
        }
        return true;
    }

    public void delete(Long memberId){
        redisTemplate.delete(KEY_PREFIX+memberId);
    }


}

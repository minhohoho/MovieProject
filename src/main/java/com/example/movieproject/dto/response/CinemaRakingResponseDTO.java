package com.example.movieproject.dto.response;

import lombok.*;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaRakingResponseDTO {

    private String cinemaName;


    public static CinemaRakingResponseDTO entityToDTO(ZSetOperations.TypedTuple<String> tuple){
        return CinemaRakingResponseDTO.builder()
                .cinemaName(tuple.getValue())
                .build();
    }

}

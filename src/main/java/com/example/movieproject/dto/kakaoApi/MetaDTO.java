package com.example.movieproject.dto.kakaoApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaDTO {

    @JsonProperty("total_count")
    private Integer totalCount;

}

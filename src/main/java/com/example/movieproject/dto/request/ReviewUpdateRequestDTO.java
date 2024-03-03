package com.example.movieproject.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewUpdateRequestDTO {

    private String content;

    @Min(value = 0 ,message = "0점 아래로는 평가가 안됩니다")
    @Max(value= 5,message = "5점 위로는 평가할 수 없습니다")
    @NotNull
    private double score;
}

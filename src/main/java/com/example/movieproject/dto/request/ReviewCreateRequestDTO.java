package com.example.movieproject.dto.request;

import com.example.movieproject.domain.Member;
import com.example.movieproject.domain.Movie;
import com.example.movieproject.domain.Review;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewCreateRequestDTO {

    @NotNull(message = "리뷰 내용은 반드시 작성해야합니다")
    private String content;

    @Min(value = 0 ,message = "0점 아래로는 평가가 안됩니다")
    @Max(value= 5,message = "5점 위로는 평가할 수 없습니다")
    @NotNull
    private Double score;

    public static Review dtoToEntity(ReviewCreateRequestDTO requestDTO,Long movieId,Long memberId){
        return Review.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .movie(Movie.builder()
                        .MovieId(movieId)
                        .build())
                .content(requestDTO.getContent())
                .score(requestDTO.getScore())
                .build();
    }


}

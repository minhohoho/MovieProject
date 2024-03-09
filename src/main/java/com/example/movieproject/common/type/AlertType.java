package com.example.movieproject.common.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlertType {


    REVIEW_LIKE("리뷰에 좋아요"),
    NEW_APPLICATION("영화관 스케줄에 새로운 신청");



    private final String content;
}

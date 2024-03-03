package com.example.movieproject.exceptionHandle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorList {

    NOT_EXIST_MOVIE(HttpStatus.BAD_REQUEST,"존재하지 않는 영화입니다"),
    NOT_EXIST_STAFF(HttpStatus.BAD_REQUEST,"존재하지 않는 스태프입니다"),
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST,"존재 하지 않는 회원입니다"),
    CANT_APPLY(HttpStatus.BAD_REQUEST,"예약이 완료되어 신청하실 수 없습니다"),
    NOT_MATCH_APPLICATION(HttpStatus.UNAUTHORIZED,"권한이 존재하지 않습니다"),
    NOT_EXIST_CINEMA_SCHEDULE(HttpStatus.BAD_REQUEST,"존재하지 않는 영화 스케줄입니다"),
    NOT_EXIST_MY_CINEMA(HttpStatus.BAD_REQUEST,"영화관이 존재하지않습니다"),
    NOT_MATCH_MY_CINEMA(HttpStatus.UNAUTHORIZED,"권한이 존재하지 않습니다"),
    NOT_EXIST_REVIEW(HttpStatus.BAD_REQUEST,"존재하지 않는 리뷰입니다");


    private final HttpStatus httpStatus;
    private final String errorMessage;


}

package com.example.movieproject.exceptionHandle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorList {

    NOT_EXIST_MOVIE(HttpStatus.BAD_REQUEST,"존재하지 않는 영화입니다"),
    NOT_EXIST_STAFF(HttpStatus.BAD_REQUEST,"존재하지 않는 스태프입니다");


    private final HttpStatus httpStatus;
    private final String errorMessage;


}

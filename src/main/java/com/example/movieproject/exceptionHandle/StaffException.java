package com.example.movieproject.exceptionHandle;

import lombok.Getter;

@Getter
public class StaffException extends RuntimeException{

    private final ErrorList errorList;
    private final int status;
    private final String errorMessage;

    public StaffException(ErrorList errorList){
        super(errorList.getErrorMessage());
        this.errorList=errorList;
        this.status=errorList.getHttpStatus().value();
        this.errorMessage=errorList.getErrorMessage();
    }


}

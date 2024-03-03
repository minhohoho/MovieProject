package com.example.movieproject.exceptionHandle;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorList errorList;
    private final int status;
    private final String errorMessage;

    public ApplicationException(ErrorList errorList){
        super(errorList.getErrorMessage());
        this.errorList=errorList;
        this.status=errorList.getHttpStatus().value();
        this.errorMessage=errorList.getErrorMessage();
    }

}

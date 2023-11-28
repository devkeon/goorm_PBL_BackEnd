package com.goormthon.tricount.utils;

import com.goormthon.tricount.TricountApiErrorCode;

public class TricountApiException extends RuntimeException {

    private TricountApiErrorCode errorCode;

    public TricountApiException(String message, TricountApiErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TricountApiErrorCode getErrorCode() {
        return errorCode;
    }
}

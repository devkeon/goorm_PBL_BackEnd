package com.goormthon.tricount.utils;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.model.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TricountApiExceptionHandler {

    @ExceptionHandler(TricountApiException.class)
    public ApiResponse<Object> apiExceptionHandler(TricountApiException e, HttpServletResponse response) {
        TricountApiErrorCode errorCode = e.getErrorCode();
        response.setStatus(HttpServletResponse.SC_OK);
        return new ApiResponse().fail(errorCode.getCode(), e.getMessage());
    }
}

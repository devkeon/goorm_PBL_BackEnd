package com.goormthon.tricount.controller;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.utils.TricountApiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ErrorController {

    @GetMapping("/error/api")
    public void error(HttpServletRequest request){
        Object message = request.getAttribute("message");
        Object errorCode = request.getAttribute("errorCode");
        throw new TricountApiException((String) message, (TricountApiErrorCode) errorCode);
    }
}

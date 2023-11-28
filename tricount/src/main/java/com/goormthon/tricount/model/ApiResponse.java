package com.goormthon.tricount.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"status","results"})
public class ApiResponse<T> implements Serializable {

    private static ApiResponseStatus OK = new ApiResponseStatus(2000, "OK");

    private ApiResponseStatus status;
    private List<T> results;

    public ApiResponse<T> ok(){
        this.status = OK;
        return this;
    }

    public ApiResponse<T> ok(List<T> results){
        this.results = results;
        this.status = OK;
        return this;
    }

    public ApiResponse<T> ok(T result){
        return ok(Collections.singletonList(result));
    }

    public ApiResponse<T> fail(int code, String message){
        ApiResponseStatus apiResponseStatus = new ApiResponseStatus(code, message);
        this.status = apiResponseStatus;
        return this;
    }

    @NoArgsConstructor
    public static class ApiResponseStatus implements Serializable{
        @Getter
        private int statusCode;
        @Getter
        private String message;

        public ApiResponseStatus(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }
}

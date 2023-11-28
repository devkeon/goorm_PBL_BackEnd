package pbl.goorm.board.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pbl.goorm.board.model.ApiResponseStatus;

@Getter
@Setter
public class ApiResponse {

    public static int RESPONSE_CONST = 2000;

    private int statusCode;
    private ApiResponseStatus responseStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;

    public ApiResponse(int statusCode, ApiResponseStatus responseStatus) {
        this.statusCode = statusCode;
        this.responseStatus = responseStatus;
    }

    public ApiResponse(int statusCode, ApiResponseStatus responseStatus, String errorMessage) {
        this.statusCode = statusCode;
        this.responseStatus = responseStatus;
        this.errorMessage = errorMessage;
    }
}

package com.goormthon.tricount.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.model.ApiResponse;
import com.goormthon.tricount.utils.SessionConst;
import com.goormthon.tricount.utils.TricountApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(new ApiResponse().fail(TricountApiErrorCode.LOGIN_NEEDED.getCode(),
                    TricountApiErrorCode.LOGIN_NEEDED.getMessage()));
            response.setContentType("applicationi/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
        return true;
    }
}

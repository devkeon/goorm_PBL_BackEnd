package com.goormthon.tricount.controller;

import com.goormthon.tricount.dto.LoginDto;
import com.goormthon.tricount.dto.SignupDto;
import com.goormthon.tricount.model.ApiResponse;
import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.service.MemberService;
import com.goormthon.tricount.utils.SessionConst;
import com.goormthon.tricount.utils.TricountApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService service;

    @PostMapping("/login")
    public ApiResponse login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request) {
        try{
            Member member = service.login(loginDto.getLoginId(), loginDto.getPassword());
            //로그인 성공 처리
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
            return new ApiResponse().ok();
        } catch (TricountApiException e){
            return new ApiResponse().fail(e.getErrorCode().getCode(), e.getMessage());
        }
    }

    @PostMapping("/join")
    public ApiResponse<Member> join(@Validated @RequestBody SignupDto signupDto) {
        Member member = Member.builder()
                .loginId(signupDto.getLoginId())
                .name(signupDto.getName())
                .password(signupDto.getPassword())
                .build();
        return new ApiResponse<Member>().ok(service.signup(member));
    }

    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return new ApiResponse().ok();
    }

}

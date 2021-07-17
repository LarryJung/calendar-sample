package com.fccalendar.api.controller.api;

import com.fccalendar.api.dto.UserSignInReq;
import com.fccalendar.api.dto.UserSignUpReq;
import com.fccalendar.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/sign-up")
    public String login(@Valid @RequestBody UserSignUpReq userSignUpReq, HttpSession httpSession) {
        loginService.signUp(userSignUpReq, httpSession);
        return "OK";
    }

    @PostMapping("/api/sign-in")
    public String signIn(@Valid @RequestBody UserSignInReq userSignInReq, HttpSession httpSession) {
        loginService.signIn(userSignInReq, httpSession);
        return "OK";
    }

    @PostMapping("/api/sign-out")
    public String signOut(HttpSession httpSession) {
        loginService.signOut(httpSession);
        return "OK";
    }
}


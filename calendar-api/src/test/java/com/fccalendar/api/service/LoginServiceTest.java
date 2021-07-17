package com.fccalendar.api.service;

import com.fccalendar.api.dto.UserCreateReq;
import com.fccalendar.api.dto.UserSignInReq;
import com.fccalendar.api.dto.UserSignUpReq;
import com.fccalendar.core.domain.User;
import com.fccalendar.core.util.BCryptEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static com.fccalendar.api.service.LoginService.LOGIN_SESSION_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final ScheduleService scheduleService = Mockito.mock(ScheduleService.class);
    private final LoginService loginService = new LoginService(userService, scheduleService);

    @Test
    @DisplayName("회원가입 - 성공")
    void test1() {
        final HttpSession session = new MockHttpSession();

        when(userService.create(any(UserCreateReq.class)))
                .thenReturn(User.builder().id(1L).build());

        loginService.signUp(new UserSignUpReq("name",
                                              "email",
                                              "pw",
                                              LocalDate.of(2000,
                                                           1,
                                                           1)),
                            session);
        verify(scheduleService, new Times(1)).createEvent(any(), any());
        assertEquals(1L,
                     (Long) session.getAttribute(LOGIN_SESSION_KEY));
    }

    @Test
    @DisplayName("sign in - 세션이 있을 때")
    void test2() {
        final HttpSession session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY,
                             1L);

        loginService.signIn(new UserSignInReq("email",
                                              "pw"),
                            session);

        verify(userService,
               new Times(0)).create(any());
    }

    @Test
    @DisplayName("sign in = 세션이 없을때 & 비밀번호 통과")
    void test3() {
        final String password = "pw";
        final HttpSession session = new MockHttpSession();
        when(userService.findByEmail(any())).thenReturn(User.builder()
                                                            .id(1L)
                                                            .password(new BCryptEncryptor().encrypt(password))
                                                            .build());
        loginService.signIn(new UserSignInReq("email",
                                              password),
                            session);
        assertEquals(1L,
                     (Long) session.getAttribute(LOGIN_SESSION_KEY));
    }

    @Test
    @DisplayName("sign in = 세션이 없을때 & 비밀번호 실패")
    void test4() {
        final String password = "pw";
        final HttpSession session = new MockHttpSession();
        when(userService.findByEmail(any()))
                .thenReturn(User.builder()
                                .id(1L)
                                .password("pw")
                                .build());
        final RuntimeException ex = assertThrows(RuntimeException.class,
                                           () -> loginService.signIn(new UserSignInReq("email", password), session));

        assertEquals("비밀번호 불일치", ex.getMessage());
    }
}

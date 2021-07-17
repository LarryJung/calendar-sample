package com.fccalendar.api.service;

import com.fccalendar.api.dto.*;
import com.fccalendar.core.domain.User;
import com.fccalendar.core.exception.CalendarException;
import com.fccalendar.core.util.BCryptEncryptor;
import com.fccalendar.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.fccalendar.core.exception.ErrorCode.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class LoginService {
    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final Encryptor encryptor = new BCryptEncryptor();

    @Transactional
    public void signUp(UserSignUpReq req, HttpSession session) {
        final User newUser = userService.create(new UserCreateReq(req.getName(),
                                                                  req.getEmail(),
                                                                  encryptor.encrypt(req.getPassword()),
                                                                  req.getBirthday()));
        if (req.getBirthday() != null) {
            scheduleService.createNotification(new AuthUser(newUser.getId()),
                                               new NotificationCreateReq("생일",
                                                                         req.getBirthday()
                                                                            .atStartOfDay(),
                                                                         null));
        }
        session.setAttribute(LOGIN_SESSION_KEY,
                             newUser.getId());
    }

    @Transactional
    public void signIn(UserSignInReq req, HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) {
            return;
        }
        final User user = userService.findByEmail(req.getEmail());
        if (encryptor.isMatch(req.getPassword(),
                              user.getPassword())) {
            session.setAttribute(LOGIN_SESSION_KEY,
                                 user.getId());
        } else {
            throw new CalendarException(PASSWORD_NOT_MATCH);
        }
    }

    public void signOut(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }

}

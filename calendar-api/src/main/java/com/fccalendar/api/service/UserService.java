package com.fccalendar.api.service;

import com.fccalendar.core.domain.User;
import com.fccalendar.api.dto.UserCreateReq;
import com.fccalendar.core.domain.repository.UserRepository;
import com.fccalendar.core.exception.CalendarException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fccalendar.core.exception.ErrorCode.ALREADY_EXISTS_USER;
import static com.fccalendar.core.exception.ErrorCode.USER_NOT_FOUND;

/**
 * UserService 는 User 도메인의 기본적인 CRUD를 담당한다.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO 테스트 코드 추가
    @Transactional
    public User create(UserCreateReq req) {
        userRepository.findByEmail(req.getEmail())
                      .ifPresent(u -> {
                          throw new CalendarException(ALREADY_EXISTS_USER);
                      });
        return userRepository.save(User.builder()
                                       .name(req.getName())
                                       .password(req.getPassword())
                                       .email(req.getEmail())
                                       .birthday(req.getBirthday())
                                       .build());
    }

    @Transactional
    public User findById(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new CalendarException(USER_NOT_FOUND));
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new CalendarException(USER_NOT_FOUND));
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean isExistedUser(Long userId) {
        return userRepository.findById(userId).isPresent();
    }
}

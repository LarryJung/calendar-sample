package com.fccalendar.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserSignUpReq {
    @NotNull
    private final String name;
    @Email(message = "유효하지 않은 이메일입니다.")
    private final String email;
    @Size(min = 6, message = "6자리 이상 입력해주세요.")
    private final String password;
    private final LocalDate birthday;
}

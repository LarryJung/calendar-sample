package com.fccalendar.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateReq {
    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;
}

package com.fccalendar.core.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate birthday;
}

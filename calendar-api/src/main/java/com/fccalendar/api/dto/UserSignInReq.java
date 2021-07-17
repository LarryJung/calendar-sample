package com.fccalendar.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserSignInReq {

    @NotNull
    private final String email;
    @NotNull
    private final String password;
}

package com.fccalendar.api.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SendMailBatchReq {
    @NotNull
    private Long id;
    @NotNull
    private LocalDateTime startAt;
    @NotBlank
    private String title;
    @NotBlank
    @Email
    private String userEmail;
}


package com.fccalendar.api.dto;

import com.fccalendar.core.domain.dto.RequestReplyType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReplyEngagementReq {
    @NotNull
    private final RequestReplyType type;
}

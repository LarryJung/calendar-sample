package com.fccalendar.core.domain.dto;

import com.fccalendar.core.domain.RequestStatus;
import lombok.Data;

@Data
public class EngagementDto {
    private final Long id;
    private final EventDto event;
    private final UserDto attendee;
    private final RequestStatus status;
}

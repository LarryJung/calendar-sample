package com.fccalendar.core.domain.dto;

import com.fccalendar.core.domain.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto implements ScheduleDto {
    private final Long scheduleId;
    private final UserDto writer;
    private final String title;
    private final LocalDateTime notifyAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}

package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.NotificationDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class Notification {
    @Getter(value = AccessLevel.PACKAGE)
    private final Schedule schedule;
    private final User writer;
    private final String title;
    private final LocalDateTime notifyAt;

    public NotificationDto toDto() {
        return new NotificationDto(schedule.getId(), writer.toDto(), title, notifyAt);
    }
}

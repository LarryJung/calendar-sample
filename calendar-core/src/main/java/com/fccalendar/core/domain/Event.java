package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.EventDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class Event {
    @Getter(value = AccessLevel.PACKAGE)
    private final Schedule schedule;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String title;
    private final String description;
    private final User writer;

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return !(this.endAt.isBefore(startAt) || this.startAt.isAfter(endAt));
    }

    public EventDto toDto() {
        return new EventDto(schedule.getId(), startAt, endAt, title, description, writer.toDto());
    }
}

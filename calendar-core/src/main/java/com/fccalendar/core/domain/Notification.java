package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.NotificationDto;

import java.time.LocalDateTime;

public class Notification {
    private final Schedule schedule;

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getNotifyAt() {
        return schedule.getStartAt();
    }

    public String getTitle() {
        return schedule.getTitle();
    }

    public String getDescription() {
        return schedule.getDescription();
    }

    public User getWriter() {
        return schedule.getWriter();
    }

    public NotificationDto toDto() {
        return new NotificationDto(schedule.getId(), getWriter().toDto(), getTitle(), getNotifyAt());
    }
}

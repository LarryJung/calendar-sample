package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.EventDto;

import java.time.LocalDateTime;

public class Event {
    private final Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }

    Schedule getSchedule() {
        return schedule;
    }

    public LocalDateTime getStartAt() {
        return schedule.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return schedule.getEndAt();
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

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return !(this.getEndAt().isBefore(startAt) || this.getStartAt().isAfter(endAt));
    }

    public EventDto toDto() {
        return new EventDto(schedule.getId(), getStartAt(), getEndAt(), getTitle(), getDescription(), getWriter().toDto());
    }
}

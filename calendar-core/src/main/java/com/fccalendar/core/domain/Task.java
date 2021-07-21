package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.TaskDto;

import java.time.LocalDateTime;

public class Task {
    private final Schedule schedule;

    public Task(Schedule schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime taskAt() {
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

    public TaskDto toRes() {
        return new TaskDto(schedule.getId(),
                           getWriter().toDto(),
                           getTitle(),
                           getDescription(),
                           taskAt());
    }
}

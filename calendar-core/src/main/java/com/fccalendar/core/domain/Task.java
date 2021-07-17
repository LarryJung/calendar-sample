package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.TaskDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class Task{
    @Getter(value = AccessLevel.PACKAGE)
    private final Schedule schedule;
    private final User writer;
    private final String title;
    private final String description;
    private final LocalDateTime taskAt;

    public TaskDto toRes() {
        return new TaskDto(schedule.getId(), writer.toDto(), title, description, taskAt);
    }
}

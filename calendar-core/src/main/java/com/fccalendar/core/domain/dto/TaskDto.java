package com.fccalendar.core.domain.dto;

import com.fccalendar.core.domain.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto implements ScheduleDto {
    private final Long scheduleId;
    private final UserDto writer;
    private final String title;
    private final String description;
    private final LocalDateTime taskAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}

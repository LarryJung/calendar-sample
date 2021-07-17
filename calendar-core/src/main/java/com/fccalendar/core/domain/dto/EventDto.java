package com.fccalendar.core.domain.dto;

import com.fccalendar.core.domain.ScheduleType;
import com.fccalendar.core.util.Period;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto implements ScheduleDto {
    private final Long scheduleId;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String title;
    private final String description;
    private final UserDto writer;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.EVENT;
    }

    public Period getPeriod() {
        return Period.of(startAt, endAt);
    }
}

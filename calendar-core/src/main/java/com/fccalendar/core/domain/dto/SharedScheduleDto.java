package com.fccalendar.core.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SharedScheduleDto {
    private final Long userId;
    private final String name;
    private final Boolean me;
    private final List<ScheduleDto> scheduleResList;
}

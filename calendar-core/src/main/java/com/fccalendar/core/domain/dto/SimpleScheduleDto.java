package com.fccalendar.core.domain.dto;

import com.fccalendar.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SimpleScheduleDto {
    private Long id;
    private String title;
    private String description;
    private Long writerId;
    private ScheduleType scheduleType;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime notifyAt;
    private LocalDateTime taskAt;
}

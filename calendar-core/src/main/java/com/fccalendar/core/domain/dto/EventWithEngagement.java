package com.fccalendar.core.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventWithEngagement {
    private final EventDto event;
    private final List<EngagementDto> engagements;
}

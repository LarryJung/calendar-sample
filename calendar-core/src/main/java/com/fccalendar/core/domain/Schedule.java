package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.ScheduleDto;
import com.fccalendar.core.exception.CalendarException;
import com.fccalendar.core.exception.ErrorCode;
import com.fccalendar.core.util.Period;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity{

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;

    @JoinColumn(name = "writer_id")
    @ManyToOne(optional = false)
    private User writer;

    @Enumerated(value = EnumType.STRING)
    private ScheduleType scheduleType;

    public static Schedule event(LocalDateTime startAt, LocalDateTime endAt, String title,
                                 String description, User writer) {
        return Schedule.builder()
                       .startAt(startAt)
                       .endAt(endAt)
                       .title(title)
                       .description(description)
                       .writer(writer)
                       .scheduleType(ScheduleType.EVENT)
                       .build();
    }

    public static Schedule task(String title, String description, LocalDateTime taskAt,
                                User writer) {
        return Schedule.builder()
                       .startAt(taskAt)
                       .title(title)
                       .description(description)
                       .writer(writer)
                       .scheduleType(ScheduleType.TASK)
                       .build();
    }

    public static Schedule notification(LocalDateTime notifyAt, String title, User writer) {
        return Schedule.builder()
                       .startAt(notifyAt)
                       .title(title)
                       .writer(writer)
                       .scheduleType(ScheduleType.NOTIFICATION)
                       .build();
    }

    public Task toTask() {
        return new Task(this);
    }

    public Event toEvent() {
        return new Event(this);
    }

    public Notification toNotification() {
        return new Notification(this);
    }

    public ScheduleDto toDto() {
        switch (scheduleType) {
            case EVENT:
                return toEvent()
                        .toDto();
            case TASK:
                return toTask()
                        .toRes();
            case NOTIFICATION:
                return toNotification().toDto();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }

    public boolean isOverlapped(LocalDate date) {
        return Period.of(this.startAt, this.endAt)
                     .isOverlapped(date);
    }

    public boolean isOverlapped(Period period) {
        return Period.of(this.startAt, this.endAt)
                     .isOverlapped(period);
    }
}

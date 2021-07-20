package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.EngagementDto;
import com.fccalendar.core.util.Period;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engagements")
public class Engagement extends BaseEntity {
    @JoinColumn(name = "schedule_id")
    @ManyToOne(optional = false)
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne(optional = false)
    @Getter
    private User attendee;

    @Getter
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public Event getEvent() {
        return schedule.toEvent();
    }

    public boolean isRequested() {
        return this.status == RequestStatus.REQUESTED;
    }
    public Engagement accept() {
        this.status = RequestStatus.ACCEPTED;
        return this;
    }

    public Engagement reject() {
        this.status = RequestStatus.REJECTED;
        return this;
    }

    public static Engagement of(Event event, User attendee) {
        return Engagement.builder()
                         .schedule(event.getSchedule())
                         .attendee(attendee)
                         .status(RequestStatus.REQUESTED)
                         .build();
    }

    public EngagementDto toDto() {
        return new EngagementDto(super.getId(), getEvent().toDto(), attendee.toDto(), status);
    }

    public boolean isOverlapped(LocalDate date) {
        return this.schedule.isOverlapped(date);
    }

    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }
}

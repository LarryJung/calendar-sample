package com.fccalendar.core.domain.repository;


import com.fccalendar.core.domain.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByAttendeeIdInAndSchedule_EndAtAfter(List<Long> attendeeIds, LocalDateTime startAt);
    List<Engagement> findAllByAttendeeId(Long attendeeIds);
}

package com.fccalendar.api.controller.api;

import com.fccalendar.api.dto.*;
import com.fccalendar.api.service.EngagementService;
import com.fccalendar.api.service.ScheduleService;
import com.fccalendar.api.service.ShareService;
import com.fccalendar.core.domain.RequestStatus;
import com.fccalendar.core.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequestMapping("/api/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ShareService shareService;
    private final ScheduleService scheduleService;
    private final EngagementService engagementService;

    @PostMapping("/tasks")
    public TaskDto createTask(@Valid @RequestBody TaskCreateReq taskCreateReq,
                              AuthUser authUser) {
        return scheduleService.createTask(authUser, taskCreateReq);
    }

    @PostMapping("/notifications")
    public List<NotificationDto> createTask(
            @Valid @RequestBody NotificationCreateReq notificationCreateReq,
            AuthUser authUser) {
        return scheduleService.createNotification(authUser, notificationCreateReq);
    }

    @PostMapping("/events")
    public EventWithEngagement createEvent(
            @Valid @RequestBody EventCreateReq eventCreateReq,
            AuthUser authUser) {
        return scheduleService.createEvent(authUser, eventCreateReq);
    }

    @PostMapping("/events/engagements")
    public RequestStatus updateEngagement(
            @Valid @RequestBody ReplyEngagementReq replyEngagementReq,
            AuthUser authUser) {
        return engagementService.update(authUser, replyEngagementReq);
    }

    @GetMapping("/day")
    public List<SharedScheduleDto> getSchedulesByDay(
            AuthUser authUser,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return scheduleService.getSchedulesByDay(authUser, date);
    }

    @GetMapping("/week")
    public List<SharedScheduleDto> getSchedulesByWeek(
            AuthUser authUser,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek
    ) {
        return scheduleService.getSchedulesByWeek(authUser,
                                                  startOfWeek == null ? LocalDate.now() :
                                                          startOfWeek);
    }

    @GetMapping("/month")
    public List<SharedScheduleDto> getSchedulesByDay(
            AuthUser authUser,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") String yearMonth
    ) {
        return scheduleService.getSchedulesByMonth(authUser,
                                                   yearMonth == null ? YearMonth.now() :
                                                           YearMonth.parse(yearMonth));
    }

    @PostMapping("/shares")
    public void shareSchedule(
            AuthUser authUser,
            @Valid @RequestBody ShareCreateReq shareCreateReq
    ) {
        shareService.createShare(authUser.getId(),
                                 shareCreateReq.getToUserId(),
                                 shareCreateReq.getDirection());
    }

    @PostMapping("/shares/{shareId}")
    public void replyToShareRequest(
            @PathVariable Long shareId,
            @RequestParam RequestReplyType type,
            AuthUser authUser
    ) {
        shareService.replyToShareRequest(shareId, authUser.getId(), type);
    }
}

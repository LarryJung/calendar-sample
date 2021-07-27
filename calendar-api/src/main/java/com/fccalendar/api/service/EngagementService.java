package com.fccalendar.api.service;

import com.fccalendar.api.dto.AuthUser;
import com.fccalendar.core.domain.Engagement;
import com.fccalendar.core.domain.RequestStatus;
import com.fccalendar.core.domain.dto.RequestReplyType;
import com.fccalendar.core.domain.repository.EngagementRepository;
import com.fccalendar.core.exception.CalendarException;
import com.fccalendar.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EngagementService {
    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser,
                                Long engagementId,
                                RequestReplyType requestReplyType) {
        return engagementRepository.findById(engagementId)
                                   .filter(Engagement::isRequested)
                                   .map(engagement -> {
                                       if (!engagement.getAttendee()
                                                      .getId()
                                                      .equals(authUser.getId())) {
                                           return null;
                                       }
                                       switch (requestReplyType) {
                                           case ACCEPT:
                                               return engagement.accept();
                                           case REJECT:
                                               return engagement.reject();
                                           default:
                                               return null;
                                       }
                                   })
                                   .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                                   .getStatus();
    }
}

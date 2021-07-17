package com.fccalendar.api.dto;


import com.fccalendar.core.util.Period;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngagementEmailStuff {
    private static final String engagementUpdateApi = "http://localhost:8080/events/engagements/";
    private final Long engagementId;
    private final String toEmail;
    private final List<String> attendeeEmails;
    private final String title;
    private final Period period;
    private final String periodStr;

    public EngagementEmailStuff(Long engagementId,
                                String toEmail,
                                List<String> attendeeEmails,
                                String title,
                                Period period) {
        this.engagementId = engagementId;
        this.toEmail = toEmail;
        this.attendeeEmails = attendeeEmails;
        this.title = title;
        this.period = period;
        this.periodStr = getPeriodStr();
    }

    // 재귀로 바꿀 수 있음..
    private String getPeriodStr() {
        final String startAtFormat = "yyyy년 MM월 dd일(E) a HH시 mm분";
        String endAtFormat = "yyyy년 MM월 dd일(E) a HH시 mm분";
        if (period.getEndAt()
                  .getYear() == period.getStartAt()
                                      .getYear()) {
            endAtFormat = endAtFormat.replace("yyyy년 ", "");
            if (period.getEndAt()
                      .getMonth() == period.getStartAt()
                                           .getMonth()) {
                endAtFormat = endAtFormat.replace("MM월 ", "");
                if (period.getEndAt()
                          .getDayOfMonth() == period.getStartAt()
                                                    .getDayOfMonth()) {
                    endAtFormat = endAtFormat.replace("dd일(E) ", "");
                }
            }
        }
        return period.getStartAt()
                     .format(DateTimeFormatter.ofPattern(startAtFormat)) + " - "
                + period.getEndAt()
                        .format(DateTimeFormatter.ofPattern(endAtFormat));
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getSubject() {
        return new StringBuilder()
                .append("[초대장] ")
                .append(title)
                .append(" - ")
                .append(periodStr)
                .append("(")
                .append(toEmail)
                .append(")")
                .toString();
    }

    public Map<String, Object> getProps() {
        final Map<String, Object> props = new HashMap<>();
        props.put("title", title);
        props.put("calendar", toEmail);
        props.put("period", periodStr);
        props.put("attendees", attendeeEmails);
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=ACCEPT");
        props.put("rejectUrl", engagementUpdateApi + engagementId + "?type=REJECT");
        return props;
    }

}

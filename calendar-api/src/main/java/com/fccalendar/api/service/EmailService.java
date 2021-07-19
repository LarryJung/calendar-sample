package com.fccalendar.api.service;

import com.fccalendar.api.dto.EngagementEmailStuff;
import com.fccalendar.api.dto.SendMailBatchReq;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender emailSender;

    @Async
    public void sendAlarmMail(SendMailBatchReq req) {
        final MimeMessagePreparator preparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(req.getUserEmail());
            helper.setSubject("[" + DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) a HH시 mm분")
                                                     .format(req.getStartAt()) + "] " + req.getTitle());
            helper.setText("알람!");
        };
        emailSender.send(preparator);
    }

    @Async
    public void send(EngagementEmailStuff stuff) {
        final MimeMessagePreparator preparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(stuff.getToEmail());
            helper.setSubject(stuff.getSubject());
            helper.setText(
                    templateEngine.process("engagement-email",
                                           new Context(Locale.KOREAN, stuff.getProps())), true);
        };
        emailSender.send(preparator);
    }
}

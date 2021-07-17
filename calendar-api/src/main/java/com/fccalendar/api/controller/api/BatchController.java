package com.fccalendar.api.controller.api;

import com.fccalendar.api.dto.SendMailBatchReq;
import com.fccalendar.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BatchController {

    private final EmailService emailService;

    @PostMapping("/api/batch/send/mail")
    public ResponseEntity<Void> sendMail(@Valid @RequestBody List<SendMailBatchReq> req) {
        req.forEach(emailService::sendAlarmMail);
        return ResponseEntity.ok().build();
    }
}

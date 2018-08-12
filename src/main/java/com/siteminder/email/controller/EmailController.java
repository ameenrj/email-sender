package com.siteminder.email.controller;

import com.siteminder.email.domain.common.EmailDTO;
import com.siteminder.email.domain.payload.PayloadResponse;
import com.siteminder.email.service.EmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    private EmailSendService emailSendService;

    public EmailController(EmailSendService emailSendService) {
        this.emailSendService = emailSendService;
    }

    @PostMapping("/send")
    public String sendEmail(@Valid @RequestBody EmailDTO email) {
        try {
            PayloadResponse response = emailSendService.sendEmail(email);
            return response.toString();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new RuntimeException(e.toString());
        }
    }
}

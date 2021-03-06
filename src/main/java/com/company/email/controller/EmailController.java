package com.company.email.controller;

import com.company.email.domain.common.EmailDTO;
import com.company.email.service.EmailSendService;
import com.company.email.domain.payload.PayloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

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
            logger.error(e.toString());
            return new PayloadResponse(HttpStatus.INTERNAL_SERVER_ERROR, e).toString();
        }
    }
}

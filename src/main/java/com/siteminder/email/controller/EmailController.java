package com.siteminder.email.controller;

import com.siteminder.email.domain.Email;
import com.siteminder.email.service.MailgunSendService;
import com.siteminder.email.service.SendGridSendService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private Logger logger = LoggerFactory.getLogger(EmailController.class);

    private MailgunSendService mailgunSendService;
    private SendGridSendService sendGridSendService;

    @Value("${gateway.mailgun.send.from}")
    private String fromAddress;

    public EmailController(MailgunSendService mailgunSendService,
                           SendGridSendService sendGridSendService) {
        this.mailgunSendService = mailgunSendService;
        this.sendGridSendService = sendGridSendService;
    }

    @RequestMapping("/")
    public String index() {
        return "Congratulations from EmailSendController.java";
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        try {
            JSONObject jsonObject = mailgunSendService.sendEmail(email);
            return jsonObject.toString();
        } catch (Exception e) {
            logger.error(e.toString());
            return e.toString();
        }
    }
}

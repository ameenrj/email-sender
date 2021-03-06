package com.company.email.service;

import com.company.email.domain.common.EmailProvider;
import com.company.email.domain.sendGrid.SendGridRequestBody;
import com.company.email.domain.common.EmailDTO;
import com.company.email.domain.payload.PayloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSendServiceImpl implements EmailSendService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);

    @Value("${gateway.preferredMailProvider}") private String preferredMailProvider;

    private MailgunSendService mailgunSendService;
    private SendGridSendService sendGridSendService;

    public EmailSendServiceImpl(MailgunSendService mailgunSendService,
                                SendGridSendService sendGridSendService) {
        this.mailgunSendService = mailgunSendService;
        this.sendGridSendService = sendGridSendService;
    }

    @Override
    public PayloadResponse sendEmail(EmailDTO email) {
        logger.debug("First call to sendEmail");
        EmailProvider emailProvider = EmailProvider.fromString(preferredMailProvider);
        if (emailProvider == null) {
            throw new IllegalStateException("'gateway.preferredMailProvider' - " +
                    "Preferred mail provider not recognised");
        }
        PayloadResponse response;

        switch (emailProvider) {
            case MAILGUN:
                response = mailgunSendService.sendEmail(email);
                if (response.getHttpStatus().is5xxServerError()) {
                    logger.info(emailProvider.name() + " server error. Using alternate email service. " +
                            "Payload Response: " + response.toString());
                    response = sendGridSendService.sendEmail(new SendGridRequestBody(email));
                }
                break;
            case SENDGRID:
                response = sendGridSendService.sendEmail(new SendGridRequestBody(email));
                if (response.getHttpStatus().is5xxServerError()) {
                    logger.info(emailProvider.name() + " server error. Using alternate email service. " +
                            "Payload Response: " + response.toString());
                    response = mailgunSendService.sendEmail(email);
                }
                break;
            default:
                // Should never reach here because of above IllegalStateException,
                // but just in case of future EmailProvider enum additions
                throw new IllegalStateException("'gateway.preferredMailProvider' - " +
                        "Preferred mail provider not supported");
        }

        return response;
    }
}

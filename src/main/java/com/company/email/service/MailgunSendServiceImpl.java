package com.company.email.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.company.email.domain.common.EmailDTO;
import com.company.email.domain.payload.PayloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunSendServiceImpl implements MailgunSendService {

    private static final Logger logger = LoggerFactory.getLogger(MailgunSendServiceImpl.class);

    @Value("${gateway.mailgun.api}") private String api;
    @Value("${gateway.mailgun.endpoint}") private String endpoint;
    @Value("${gateway.mailgun.authorisation.key}") private String key;
    @Value("${gateway.mailgun.domain}") private String domain;
    @Value("${gateway.connectTimeout}") private long connectTimeout;
    @Value("${gateway.socketTimeout}") private long socketTimeout;

    @Override
    public PayloadResponse sendEmail(EmailDTO email) {
        logger.debug("Sending email using Mailgun");

        try {
            Unirest.setTimeouts(connectTimeout, socketTimeout);
            HttpResponse<String> response = Unirest.post(api + domain + endpoint)
                    .basicAuth("api", key)
                    .queryString("from", email.getFrom())
                    .queryString("to", email.getTo())
                    .queryString("cc", email.getCc())
                    .queryString("bcc", email.getBcc())
                    .queryString("subject", email.getSubject())
                    .queryString("text", email.getBody())
                    .asString();

            return new PayloadResponse(response);
        } catch (UnirestException e) {
            throw new RuntimeException("Error occurred while sending email through Mailgun: " + e.getMessage());
        }
    }
}

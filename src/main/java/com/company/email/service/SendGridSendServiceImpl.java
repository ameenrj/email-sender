package com.company.email.service;

import com.company.email.domain.sendGrid.SendGridRequestBody;
import com.company.email.util.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.company.email.domain.payload.PayloadResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridSendServiceImpl implements SendGridSendService {

    private static final Logger logger = LoggerFactory.getLogger(MailgunSendServiceImpl.class);

    @Value("${gateway.sendGrid.api}") private String api;
    @Value("${gateway.sendGrid.endpoint}") private String endpoint;
    @Value("${gateway.sendGrid.authorisation.key}") private String key;
    @Value("${gateway.connectTimeout}") private long connectTimeout;
    @Value("${gateway.socketTimeout}") private long socketTimeout;

    @Override
    public PayloadResponse sendEmail(SendGridRequestBody requestBody) {
        logger.debug("Sending email using SendGrid");

        try {
            Unirest.setTimeouts(connectTimeout, socketTimeout);
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse<String> response = Unirest.post(api + endpoint)
                    .header(RequestUtil.AUTHORIZATION, RequestUtil.BEARER + RequestUtil.SPACE + key)
                    .header(RequestUtil.CONTENT_TYPE, RequestUtil.CONTENT_JSON)
                    .body(objectMapper.writeValueAsString(requestBody))
                    .asString();

            return new PayloadResponse(response);
        } catch (JsonProcessingException e) {
            throw new JSONException("Error occurred while getting JSON Object: " + e.toString());
        } catch (UnirestException e) {
            throw new RuntimeException("Error occurred while sending email through Mailgun: " + e.getMessage());
        }
    }
}

package com.siteminder.email.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.siteminder.email.domain.payload.PayloadResponse;
import com.siteminder.email.domain.sendGrid.SendGridRequestBody;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.siteminder.email.util.RequestUtil.*;

@Service
public class SendGridSendServiceImpl implements SendGridSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailgunSendServiceImpl.class);

    @Value("${gateway.sendGrid.api}") private String api;
    @Value("${gateway.sendGrid.endpoint}") private String endpoint;
    @Value("${gateway.sendGrid.authorisation.key}") private String key;

    @Override
    public PayloadResponse sendEmail(SendGridRequestBody requestBody) {
        LOGGER.debug("Sending email using SendGrid");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse<JsonNode> response = Unirest.post(api + endpoint)
                    .header(AUTHORIZATION,BEARER + SPACE + key)
                    .header(CONTENT_TYPE, CONTENT_JSON)
                    .body(objectMapper.writeValueAsString(requestBody))
                    .asJson();

            JsonNode node = response.getBody();
            if (node.isArray()) {
                throw new UnirestException("The request returns a JSON Array. Json: " +
                        node.getArray().toString(4));
            } else {
                return new PayloadResponse(response);
            }
        } catch (JsonProcessingException | UnirestException e) {
            LOGGER.error("Error occurred while getting JSON object: " + e.toString());
            throw new JSONException("Error occurred while getting JSON Object: " + e.getMessage());
        }
    }
}

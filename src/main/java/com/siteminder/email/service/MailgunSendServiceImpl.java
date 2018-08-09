package com.siteminder.email.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.siteminder.email.domain.common.EmailDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunSendServiceImpl implements MailgunSendService {

    private Logger logger = LoggerFactory.getLogger(MailgunSendServiceImpl.class);

    @Value("${gateway.mailgun.api}") private String api;
    @Value("${gateway.mailgun.endpoint}") private String endpoint;
    @Value("${gateway.mailgun.authorisation.key}") private String key;
    @Value("${gateway.mailgun.domain}") private String domain;

    public JSONObject sendEmail(EmailDTO email) {
        logger.debug("Sending email using Mailgun");

        try {
            HttpResponse<JsonNode> response = Unirest.post(api + domain + endpoint)
                    .basicAuth("api", key)
                    .queryString("from", email.getFrom())
                    .queryString("to", email.getTo())
                    .queryString("cc", email.getCc())
                    .queryString("bcc", email.getBcc())
                    .queryString("subject", email.getSubject())
                    .queryString("text", email.getBody())
                    .asJson();

            JsonNode node = response.getBody();
            if (node.isArray()) {
                throw new UnirestException("The request returns a JSON Array. Json: " +
                        node.getArray().toString(4));
            } else {
                return node.getObject();
            }
        } catch (UnirestException e) {
            throw new JSONException("Error occurred while getting JSON Object: " + e.getLocalizedMessage());
        }
    }
}

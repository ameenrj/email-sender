package com.siteminder.email.domain.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class PayloadResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadResponse.class);

    private int statusCode;
    private String statusMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
    @JsonIgnore
    private HttpStatus httpStatus;

    public PayloadResponse(HttpResponse<JsonNode> response) {
        this.httpStatus = HttpStatus.resolve(response.getStatus());
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();
        if (!httpStatus.is2xxSuccessful()) {
            this.errorMessage = response.getBody().toString();
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return "Could not retrieve PayloadResponse JSON - " + e.toString();
        }
    }
}

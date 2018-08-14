package com.company.email.domain.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class PayloadResponse {
    private static final Logger logger = LoggerFactory.getLogger(PayloadResponse.class);

    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonIgnore
    private HttpStatus httpStatus;

    public PayloadResponse(HttpStatus httpStatus, Exception e) {
        this.status = httpStatus.value();
        this.message = e.toString();
        this.httpStatus = httpStatus;
    }

    public PayloadResponse(HttpResponse<String> response) {
        this.httpStatus = HttpStatus.resolve(response.getStatus());
        this.status = httpStatus.value();
        if (httpStatus.is2xxSuccessful()) {
            this.message = httpStatus.getReasonPhrase();
        } else {
            this.message = response.getBody();
        }
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "Could not retrieve PayloadResponse JSON - " + e.toString();
        }
    }
}

package com.siteminder.email.domain;

import java.util.List;

/**
 * Email Data Transfer Object
 */
public class Email {

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String from;
    private String subject;
    private String body;

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

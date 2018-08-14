package com.company.email.domain.common;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Email Data Transfer Object for Mailgun
 */
public class EmailDTO {

    @NotEmpty
    private List<@Email String> to;
    private List<@Email String> cc;
    private List<@Email String> bcc;
    @NotEmpty
    private String from;
    @NotEmpty
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

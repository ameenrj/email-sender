package com.company.email.domain.sendGrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.company.email.domain.common.EmailDTO;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request body sent as JSON to SendGrid
 */
@JsonComponent
public class SendGridRequestBody {

    private List<Personalizations> personalizations;
    private From from;
    private List<Content> content;

    public SendGridRequestBody() {}

    public SendGridRequestBody(EmailDTO email) {
        this.personalizations = Collections.singletonList(new Personalizations(email));
        this.from = new From(email.getFrom());
        this.content = Collections.singletonList(new Content(email.getBody()));
    }

    public List<Personalizations> getPersonalizations() {
        return personalizations;
    }

    public From getFrom() {
        return from;
    }

    public List<Content> getContent() {
        return content;
    }

    public class Personalizations {
        private List<To> to;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Cc> cc;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Bcc> bcc;
        private String subject;

        Personalizations(EmailDTO email) {
            this.to = email.getTo().stream().map(To::new).collect(Collectors.toList());
            this.cc = email.getCc().stream().map(Cc::new).collect(Collectors.toList());
            this.bcc = email.getBcc().stream().map(Bcc::new).collect(Collectors.toList());
            this.subject = email.getSubject();
        }

        public List<To> getTo() {
            return to;
        }

        public List<Cc> getCc() {
            return cc;
        }

        public List<Bcc> getBcc() {
            return bcc;
        }

        public String getSubject() {
            return subject;
        }
    }

    public class To {
        private String email;

        To(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public class Cc {
        private String email;

        Cc(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public class Bcc {
        private String email;

        Bcc(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public class From {
        private String email;

        From(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public class Content {
        private String type = "text/plain";
        private String value;

        Content(String body) {
            this.value = body;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}

package com.siteminder.email.domain.common;

public enum EmailProvider {
    MAILGUN, SENDGRID;

    public static EmailProvider fromString(String value) {
        for (EmailProvider emailProvider : EmailProvider.values()) {
            if (emailProvider.name().equalsIgnoreCase(value)) {
                return emailProvider;
            }
        }
        return null;
    }
}

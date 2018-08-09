package com.siteminder.email.service;

import com.siteminder.email.domain.Email;
import org.json.JSONObject;

public interface MailgunSendService {

    JSONObject sendEmail(Email email);
}

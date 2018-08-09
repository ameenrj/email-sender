package com.siteminder.email.service;

import com.siteminder.email.domain.common.EmailDTO;
import org.json.JSONObject;

public interface MailgunSendService {

    JSONObject sendEmail(EmailDTO email);
}

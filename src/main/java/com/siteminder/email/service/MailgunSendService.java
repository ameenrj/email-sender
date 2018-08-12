package com.siteminder.email.service;

import com.siteminder.email.domain.common.EmailDTO;
import com.siteminder.email.domain.payload.PayloadResponse;

public interface MailgunSendService {

    PayloadResponse sendEmail(EmailDTO email);
}

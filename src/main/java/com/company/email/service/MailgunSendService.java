package com.company.email.service;

import com.company.email.domain.common.EmailDTO;
import com.company.email.domain.payload.PayloadResponse;

public interface MailgunSendService {

    PayloadResponse sendEmail(EmailDTO email);
}

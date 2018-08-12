package com.siteminder.email.service;

import com.siteminder.email.domain.common.EmailDTO;
import com.siteminder.email.domain.payload.PayloadResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmailSendService {
    PayloadResponse sendEmail(EmailDTO email);
}

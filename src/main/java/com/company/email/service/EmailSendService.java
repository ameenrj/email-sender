package com.company.email.service;

import com.company.email.domain.common.EmailDTO;
import com.company.email.domain.payload.PayloadResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmailSendService {
    PayloadResponse sendEmail(EmailDTO email);
}

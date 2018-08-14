package com.company.email.service;

import com.company.email.domain.sendGrid.SendGridRequestBody;
import com.company.email.domain.payload.PayloadResponse;

public interface SendGridSendService {

    PayloadResponse sendEmail(SendGridRequestBody request);
}

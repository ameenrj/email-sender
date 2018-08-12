package com.siteminder.email.service;

import com.siteminder.email.domain.payload.PayloadResponse;
import com.siteminder.email.domain.sendGrid.SendGridRequestBody;

public interface SendGridSendService {

    PayloadResponse sendEmail(SendGridRequestBody request);
}

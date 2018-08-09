package com.siteminder.email.service;

import com.siteminder.email.domain.sendGrid.SendGridRequestBody;
import org.json.JSONObject;

public interface SendGridSendService {

    JSONObject sendEmail(SendGridRequestBody request);
}

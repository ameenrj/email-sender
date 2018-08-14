package com.company.email.controller;

import com.company.email.service.EmailSendService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest()
@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @Autowired
    private static MockMvc mvc;

    @Autowired
    private static EmailSendService emailSendService;

    @Test
    void givenMailProviderAcceptsRequest_whenAnEmailIsSent_then200IsReceived() {

    }

    @Test
    void givenMailProviderErrors_whenAnEmailIsSent_then500IsReceived() {

    }

    @Test
    void givenValidSendEmailRequest_whenSendEmailEndpointIsHit_successfulValidation() {

    }

    @Test
    void givenInvalidSendEmailRequest_whenSendEmailEndpointIsHit_validationErrorIsReturned() {

    }
}

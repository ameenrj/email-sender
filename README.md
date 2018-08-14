# email-sender

## Problem

A company needs a service that when given the necessary information, sends an email to customers. However, two email
providers need to be configured so that when one email provider is down, the other is used, and customers are not
impacted.

## Proposed Solution

To use Java, Spring Boot and REST to implement a service to abstract the two email providers. When a 5xx server 
response is received (sever error), attempt to use the other email provider.

A REST API endpoint will be located at /email/send to receive necessary JSON data to send emails.

## Assumptions

- There is no need to support HTML email body types
    - (inferred) There is no need to support attachments
- An email can be sent with no body
- There is no need to support multiple "Personalizations" (using SendGrid) to specify different handling instructions for
 different recipients (See more at: https://sendgrid.com/docs/Classroom/Send/v3_Mail_Send/personalizations.html)

## Installation

Using: Java 8,, Gradle 4.9

First, clone the project:
```
git clone --recursive git@github.com:ameenrj/email-sender.git
```
Then cd into the directory of the project:
```
cd email-sender
```
Finally, build the project:
```
gradle build
```

## Running the app and sending an email

You will need your own Mailgun and Sendgrid accounts/API keys to send your emails. Register for an account first, then 
either hardcode the API keys in the application.yml file or set them as environment variables through your IDE or OS. 
You can find your API keys by following the instructions on the following pages;

Mailgun: https://help.mailgun.com/hc/en-us/articles/203380100-Where-can-I-find-my-API-key-and-SMTP-credentials-
SendGrid: https://sendgrid.com/docs/Classroom/Send/How_Emails_Are_Sent/api_keys.html

Run the application by pointing your IDE to the main class. Alternatively, run;
```
java -jar build/libs/email-sender-0.0.1-SNAPSHOT.jar
```

Using curl or a HTTP client such as Postman, POST the following JSON data to localhost:8080/email/send;

```json
{
    "to": ["example@example.com"],
    "cc": ["cc@example.com"],
    "bcc": ["bcc@example.com"],
    "from": "Excited Tester <tester@company.org>",
    "subject": "Example Subject",
    "body": "This is an example body"
}
```

NOTE: Make sure Mailgun and SendGrid allow you to send emails to the email addresses you have provided (e.g. a 
limitation of a free account with Mailgun when you don't provide credit card details is the limitation to send emails
only to authorised email addresses only - those who agree to receive emails)

## Concerns

- Creation of SendGridRequestBody object seems expensive and long. *TODO*: Research for better implementation
- Unit testing should be added *TODO*: Implement unit testing
    -Example unit tests for EmailController;
        -givenMailProviderAcceptsRequest_whenAnEmailIsSent_then200IsReceived
        -givenMailProviderErrors_whenAnEmailIsSent_then500IsReceived()
        -givenValidSendEmailRequest_whenSendEmailEndpointIsHit_successfulValidation()
        -givenInvalidSendEmailRequest_whenSendEmailEndpointIsHit_validationErrorIsReturned()
- Integration test should be added *TODO*: Implement integration tests

## Thoughts

- Using the Circuit Breaker pattern from the Netflix Hystrix library seemed possible as a method of implementing
effective failover, however Fallback functionality seems intended for returning an acceptable response to the user, 
rather than making another service call that could also fail 
(See more at: https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback, https://spring.io/guides/gs/circuit-breaker/)
- The default Unirest timeouts are 10000ms to connect and 60000ms for the socket timeout. This is too high for a 
service relied on by a front-end, and so 3000ms and 10000ms have been used. These are configurable in the 
application.yaml
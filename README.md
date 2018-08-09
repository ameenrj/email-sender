# email-sender

Circuit Breaker pattern implemented using Netflix’s Hystrix library (See more at: https://spring.io/guides/gs/circuit-breaker/)

## Assumptions

- There is no need to support HTML email body types
- There is no need to support multiple "Personalizations" to specify different handling instructions for
 different recipients(See more at: https://sendgrid.com/docs/Classroom/Send/v3_Mail_Send/personalizations.html)

## Install and setup

## Concerns

- Creation of SendGridRequestBody object seems expensive and long. *TODO*: Research for better implementation
# email-sender

Circuit Breaker pattern implemented using Netflix’s Hystrix library 
(See more at: https://spring.io/guides/gs/circuit-breaker/)

## Assumptions

- There is no need to support HTML email body types
    - (inferred) There is no need to support attachments
- An email can be sent with no body
- There is no need to support multiple "Personalizations" to specify different handling instructions for
 different recipients(See more at: https://sendgrid.com/docs/Classroom/Send/v3_Mail_Send/personalizations.html)

## Install and setup

## Concerns

- Creation of SendGridRequestBody object seems expensive and long. *TODO*: Research for better implementation

## Thoughts

- Using the Circuit Breaker pattern from the Netflix Hystrix library seemed possible as a method of implementing
effective failover, however Fallback functionality seems intended for returning an acceptable response to the user, 
rather than making another service call that could also fail 
(See more at: https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback, https://spring.io/guides/gs/circuit-breaker/)
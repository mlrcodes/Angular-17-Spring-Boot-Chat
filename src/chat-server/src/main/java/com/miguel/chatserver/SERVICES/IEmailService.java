package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.EmailTemplateName;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface IEmailService {

  @Async
  public void sendEmail(
    String to,
    String userName,
    EmailTemplateName emailTemplate,
    String confirmationUrl,
    String activationCode,
    String subject
  ) throws MessagingException;

}

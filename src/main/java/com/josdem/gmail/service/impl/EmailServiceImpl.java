/*
  Copyright 2025 Jose Morales contact@josdem.io

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.josdem.gmail.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.josdem.gmail.client.GmailClient;
import com.josdem.gmail.config.ApplicationProperties;
import com.josdem.gmail.mail.MessageCreator;
import com.josdem.gmail.mail.TemplateCreator;
import com.josdem.gmail.model.MessageCommand;
import com.josdem.gmail.service.EmailService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private static final String APPLICATION_NAME = "gmailer-spring-boot";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  private final MessageCreator messageCreator;
  private final GmailClient gmailClient;
  private final TemplateCreator templateCreator;
  private final ApplicationProperties applicationProperties;

  @Override
  public boolean sendEmail(MessageCommand messageCommand)
      throws IOException, MessagingException, GeneralSecurityException, TemplateException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    var credentials = gmailClient.getCredentials(HTTP_TRANSPORT);
    var service =
        new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build();

    var mimeMessage =
        templateCreator.createMailWithTemplate(
            messageCommand, applicationProperties.getFromEmail(), "welcome.ftl");
    var message = messageCreator.createMessageWithEmail(mimeMessage);
    var result = service.users().messages().send("me", message).execute();
    log.info("result: {}", result);
    return true;
  }
}

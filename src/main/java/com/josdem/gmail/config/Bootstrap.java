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

package com.josdem.gmail.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.josdem.gmail.client.CreateEmail;
import com.josdem.gmail.client.CreateMessage;
import com.josdem.gmail.service.GmailService;
import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final HttpServer httpServer;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Application is ready to serve requests");
        try {
            httpServer.start();
            sendEmail();
            log.info("HTTP server started on port {}", httpServer.getAddress().getPort());
        } catch (Exception e) {
            log.error("Error starting HTTP server", e);
            throw new RuntimeException(e);
        }
    }

    private void sendEmail() throws GeneralSecurityException, IOException, MessagingException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Credential credentials = GmailService.getCredentials(HTTP_TRANSPORT);
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();

        var createEmail = CreateEmail.createEmail("contact@josdem.io", "vetlog@josdem.io", "Test email", "This is a test email");
        System.out.println("MimeMessage: " + createEmail);
        var message = CreateMessage.createMessageWithEmail(createEmail);
        System.out.println("Message: " + message);
        var result = service.users().messages().send("me", message).execute();
        System.out.println("Result: " + result);
    }

}
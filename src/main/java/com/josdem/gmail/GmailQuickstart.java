package com.josdem.gmail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.josdem.gmail.helper.CreateEmail;
import com.josdem.gmail.helper.CreateMessage;
import com.josdem.gmail.service.GmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GmailQuickstart {
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static void main(String... args) throws IOException, GeneralSecurityException, MessagingException {
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
package com.josdem.gmail.mail;

import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MessageCreator {

    public Message createMessageWithEmail(MimeMessage emailContent) throws IOException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        var message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}

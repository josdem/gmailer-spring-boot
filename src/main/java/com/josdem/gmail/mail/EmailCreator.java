package com.josdem.gmail.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailCreator {

    public MimeMessage create(String toEmailAddress,
                                          String fromEmailAddress,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(fromEmailAddress));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
}
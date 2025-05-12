package com.josdem.gmail.mail;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class TemplateCreator {

    private final Configuration configuration;

    public MimeMessage createMailWithTemplate(Map<String, String> model, String toEmailAddress, String fromEmailAddress, String template) throws IOException, TemplateException, MessagingException, AddressException {
        var props = new Properties();
        var session = Session.getDefaultInstance(props, null);
        var myTemplate = configuration.getTemplate(template);
        var mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(fromEmailAddress);
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmailAddress));
        var message = new MimeMessageHelper(mimeMessage, true);
        var text = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, model);
        message.setText(text, true);
        return mimeMessage;
    }
}

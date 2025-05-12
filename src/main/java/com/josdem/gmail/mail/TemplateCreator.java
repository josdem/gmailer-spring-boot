package com.josdem.gmail.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josdem.gmail.model.Command;
import com.josdem.gmail.model.MessageCommand;
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
    private final ObjectMapper mapper;

    public MimeMessage createMailWithTemplate(MessageCommand messageCommand, String fromEmail, String template) throws IOException, TemplateException, MessagingException, AddressException {
        var props = new Properties();
        var model = mapper.convertValue(messageCommand, new TypeReference<Map<String, String>>() {});
        var session = Session.getDefaultInstance(props, null);
        var myTemplate = configuration.getTemplate(template);
        var mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(messageCommand.getEmail());
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(fromEmail));
        var message = new MimeMessageHelper(mimeMessage, true);
        var text = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, model);
        message.setText(text, true);
        return mimeMessage;
    }
}

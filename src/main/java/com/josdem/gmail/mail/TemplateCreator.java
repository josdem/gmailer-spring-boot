package com.josdem.gmail.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josdem.gmail.model.MessageCommand;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
@RequiredArgsConstructor
public class TemplateCreator {

  private final Configuration configuration;
  private final ObjectMapper mapper;

  public MimeMessage createMailWithTemplate(
      MessageCommand messageCommand, String fromEmail, String template)
      throws IOException, TemplateException, MessagingException {
    var props = new Properties();
    var model = mapper.convertValue(messageCommand, new TypeReference<Map<String, String>>() {});
    var session = Session.getDefaultInstance(props, null);
    var myTemplate = configuration.getTemplate(template);
    var mimeMessage = new MimeMessage(session);
    mimeMessage.setFrom(fromEmail);
    mimeMessage.setSubject(messageCommand.getSubject());
    mimeMessage.addRecipient(
        MimeMessage.RecipientType.TO, new InternetAddress(messageCommand.getEmail()));
    var text = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, model);
    mimeMessage.setText(text, "UTF-8", "html");
    return mimeMessage;
  }
}

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

package com.josdem.gmail.mail;

import com.josdem.gmail.helper.InternetAddressHelper;
import com.josdem.gmail.helper.MimeMessageHelper;
import com.josdem.gmail.helper.SessionHelper;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCreator {

  private final Properties properties;
  private final SessionHelper sessionHelper;
  private final MimeMessageHelper mimeMessageHelper;
  private final InternetAddressHelper internetAddressHelper;

  public MimeMessage create(
      String toEmailAddress, String fromEmailAddress, String subject, String bodyText)
      throws MessagingException {

    var session = sessionHelper.createSession(properties);
    var email = mimeMessageHelper.createMimeMessage(session);

    email.setFrom(internetAddressHelper.createInternetAddress(fromEmailAddress));
    email.addRecipient(
        javax.mail.Message.RecipientType.TO,
        internetAddressHelper.createInternetAddress(toEmailAddress));
    email.setSubject(subject);
    email.setText(bodyText);
    return email;
  }
}

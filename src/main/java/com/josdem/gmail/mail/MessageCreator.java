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

import org.apache.commons.codec.binary.Base64;

import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class MessageCreator {

    public Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        var buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        var encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        var message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}

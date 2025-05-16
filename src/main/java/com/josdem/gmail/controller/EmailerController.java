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

package com.josdem.gmail.controller;

import com.josdem.gmail.exception.BusinessException;
import com.josdem.gmail.model.MessageCommand;
import com.josdem.gmail.service.EmailService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.mail.MessagingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/emailer/*")
@RequiredArgsConstructor
@Tag(name = "Emailer Controller", description = "Manages Emailer Endpoints")
public class EmailerController {

  @Value("${token}")
  private String token;

  private final EmailService emailService;

  @Operation(summary = "Sends an Email")
  @PostMapping(value = "/message", consumes = "application/json")
  @ApiResponse(responseCode = "200", description = "Message sent")
  @ApiResponse(responseCode = "403", description = "Forbidden message")
  @ApiResponse(responseCode = "401", description = "You have tried to access an Unauthorized page")
  public ResponseEntity<String> message(@RequestBody MessageCommand command)
      throws MessagingException, GeneralSecurityException, IOException, TemplateException {
    log.info("Request send email to: {}", command.getEmail());
    if (!token.equals(command.getToken())) {
      return new ResponseEntity<String>("FORBIDDEN", HttpStatus.FORBIDDEN);
    }
    emailService.sendEmail(command);
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handleException(BusinessException be) {
    return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
  }
}

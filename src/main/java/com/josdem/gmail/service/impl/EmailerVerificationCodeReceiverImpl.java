package com.josdem.gmail.service.impl;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.josdem.gmail.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmailerVerificationCodeReceiverImpl implements VerificationCodeReceiver {

  private final ApplicationProperties applicationProperties;

  @Override
  public String getRedirectUri() throws IOException {
    return applicationProperties.getCallbackUrl();
  }

  @Override
  public String waitForCode() throws IOException {
    return "";
  }

  @Override
  public void stop() throws IOException {}
}

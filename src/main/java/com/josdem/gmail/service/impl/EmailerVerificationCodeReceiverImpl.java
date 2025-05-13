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

package com.josdem.gmail.service.impl;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.josdem.gmail.config.ApplicationProperties;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

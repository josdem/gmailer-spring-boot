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

package com.josdem.gmail.service;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.util.Throwables;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.concurrent.Semaphore;

@Getter
@Setter
public class JmailerVerificationCodeReceiverImpl implements VerificationCodeReceiver {

    private static final String LOCALHOST = "localhost";
    private static final String CALLBACK_PATH = "/Callback";

    private HttpServer server;
    String code;
    String error;
    final Semaphore waitUnlessSignaled = new Semaphore(0 /* initially zero permit */);
    private int port;
    private final String host;
    private final String callbackPath;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;

    JmailerVerificationCodeReceiverImpl(
            String host,
            int port,
            String callbackPath,
            String successLandingPageUrl,
            String failureLandingPageUrl) {
        this.host = host;
        this.port = port;
        this.callbackPath = callbackPath;
        this.successLandingPageUrl = successLandingPageUrl;
        this.failureLandingPageUrl = failureLandingPageUrl;
    }

    @Override
    public String getRedirectUri() {
        return "http://" + this.getHost() + ":" + port + callbackPath;
    }

    @Override
    public String waitForCode() throws IOException {
        waitUnlessSignaled.acquireUninterruptibly();
        if (error != null) {
            throw new IOException("User authorization failed (" + error + ")");
        }
        return code;
    }

    @Override
    public void stop() throws IOException {
        waitUnlessSignaled.release();
        if (server != null) {
            try {
                server.stop(0);
            } catch (Exception e) {
                Throwables.propagateIfPossible(e);
                throw new IOException(e);
            }
            server = null;
        }
    }


    public static final class Builder {

        private String host = LOCALHOST;
        private int port = -1;
        private String successLandingPageUrl;
        private String failureLandingPageUrl;
        private String callbackPath = CALLBACK_PATH;

        public JmailerVerificationCodeReceiverImpl build() {
            return new JmailerVerificationCodeReceiverImpl(
                    host, port, callbackPath, successLandingPageUrl, failureLandingPageUrl);
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

    }

}
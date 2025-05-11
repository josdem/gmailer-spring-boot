package com.josdem.gmail.service.impl;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;

public class EmailerVerificationCodeReceiverImpl implements VerificationCodeReceiver {
    @Override
    public String getRedirectUri() throws IOException {
        return "http://localhost:8083/Callback";
    }

    @Override
    public String waitForCode() throws IOException {
        return "";
    }

    @Override
    public void stop() throws IOException {

    }
}

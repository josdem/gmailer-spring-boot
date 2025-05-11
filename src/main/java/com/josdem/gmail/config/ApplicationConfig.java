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

package com.josdem.gmail.config;

import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final GetHandler getHandler;
    private final CallbackHandler callbackHandler;
    private final ApplicationProperties applicationProperties;

    @Bean
    HttpServer httpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(applicationProperties.getPort()), 0);
            server.createContext("/Callback", callbackHandler);
            server.createContext("/get", getHandler);
            server.setExecutor(null);
            return server;
        } catch (IOException ioe) {
            throw new RuntimeException("Error starting HTTP server", ioe);
        }
    }
}
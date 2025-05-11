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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private final HttpServer httpServer;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Application is ready to serve requests");
        try {
            httpServer.start();
            log.info("HTTP server started on port {}", httpServer.getAddress().getPort());
        } catch (Exception e) {
            log.error("Error starting HTTP server", e);
            throw new RuntimeException(e);
        }
    }

}
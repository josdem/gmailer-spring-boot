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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InfoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Use /get?to=contact@josdem.io&template=welcome.ftl";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (var os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

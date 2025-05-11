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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_OK;

@Setter
@Component
public class CallbackHandler implements HttpHandler {

    private String callbackPath;
    private String error;
    private String code;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;
    final Semaphore waitUnlessSignaled = new Semaphore(0);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (!callbackPath.equals(httpExchange.getRequestURI().getPath())) {
            return;
        }

        StringBuilder body = new StringBuilder();

        try {
            Map<String, String> parms = this.queryToMap(httpExchange.getRequestURI().getQuery());
            error = parms.get("error");
            code = parms.get("code");

            Headers respHeaders = httpExchange.getResponseHeaders();
            if (error == null && successLandingPageUrl != null) {
                respHeaders.add("Location", successLandingPageUrl);
                httpExchange.sendResponseHeaders(HTTP_MOVED_TEMP, -1);
            } else if (error != null && failureLandingPageUrl != null) {
                respHeaders.add("Location", failureLandingPageUrl);
                httpExchange.sendResponseHeaders(HTTP_MOVED_TEMP, -1);
            } else {
                writeLandingHtml(httpExchange, respHeaders);
            }
            httpExchange.close();
        } finally {
            waitUnlessSignaled.release();
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        if (query != null) {
            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], pair[1]);
                } else {
                    result.put(pair[0], "");
                }
            }
        }
        return result;
    }

    private void writeLandingHtml(HttpExchange exchange, Headers headers) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(HTTP_OK, 0);
            headers.add("Content-Type", "text/html");

            OutputStreamWriter doc = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            doc.write("<html>");
            doc.write("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            doc.write("<body>");
            doc.write("Received verification code. You may now close this window.");
            doc.write("</body>");
            doc.write("</html>\n");
            doc.flush();
        }
    }
}
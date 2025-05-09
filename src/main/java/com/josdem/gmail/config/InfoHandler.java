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

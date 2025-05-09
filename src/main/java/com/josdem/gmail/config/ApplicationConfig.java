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

    private final ApplicationProperties applicationProperties;

    @Bean
    HttpServer httpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(applicationProperties.getPort()), 0);
            server.setExecutor(null);
            return server;
        } catch (IOException ioe) {
            throw new RuntimeException("Error starting HTTP server", ioe);
        }
    }
}

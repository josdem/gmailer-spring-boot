package com.josdem.gmail.config;

import com.sun.net.httpserver.HttpServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
public class ApplicationConfig {

    @Bean
    HttpServer httpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.setExecutor(null);
            return server;
        } catch (IOException e) {
            throw new RuntimeException("Error starting HTTP server", e);
        }
    }
}

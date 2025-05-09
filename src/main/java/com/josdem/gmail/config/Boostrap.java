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
public class Boostrap implements ApplicationListener<ApplicationReadyEvent> {

    private final HttpServer httpServer;
    private final InfoHandler infoHandler;
    private final GetHandler getHandler;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("Application is ready to serve requests");
        try {
            httpServer.createContext("/info", infoHandler);
            httpServer.createContext("/get", getHandler);
            httpServer.start();
            log.info("HTTP server started on port {}", httpServer.getAddress().getPort());
        } catch (Exception e) {
            log.error("Error starting HTTP server", e);
            throw new RuntimeException(e);
        }
    }

}

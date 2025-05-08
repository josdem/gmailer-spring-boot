package com.josdem.gmail;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetSocketAddress;

@Slf4j
@SpringBootApplication
public class GmailSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GmailSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			server.createContext("/hello", new HelloHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("Server started on port 8080");
		} catch (IOException e) {
			log.error("Error starting server", e);
			throw new RuntimeException(e);
		}
	}

	static class HelloHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "OK";
			exchange.sendResponseHeaders(200, response.getBytes().length);
			try (var os = exchange.getResponseBody()) {
				os.write(response.getBytes());
			}
		}
	}

}

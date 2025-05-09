package com.josdem.gmail;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

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
			server.createContext("/send", new SenderHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("Server started on port 8080");
		} catch (IOException e) {
			log.error("Error starting server", e);
			throw new RuntimeException(e);
		}
	}

	static class SenderHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "Use /get?hello=word";
			exchange.sendResponseHeaders(200, response.getBytes().length);
			try (var os = exchange.getResponseBody()) {
				os.write(response.getBytes());
			}
		}
	}

	public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}


	static class GetHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			StringBuilder response = new StringBuilder();
			Map<String,String> parms = queryToMap(httpExchange.getRequestURI().getQuery());
			response.append("<html><body>");
			response.append("hello : " + parms.get("hello") + "<br/>");
			response.append("foo : " + parms.get("foo") + "<br/>");
			response.append("</body></html>");
			writeResponse(httpExchange, response.toString());
		}
	}

	public static Map<String, String> queryToMap(String query){
		Map<String, String> result = new HashMap<>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length>1) {
				result.put(pair[0], pair[1]);
			}
			else{
				result.put(pair[0], "");
			}
		}
		return result;
	}

}

package com.josdem.gmail.config;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        StringBuilder response = new StringBuilder();
        Map<String,String> parms = queryToMap(httpExchange.getRequestURI().getQuery());
        response.append("<html><body>");
        response.append("to : ").append(parms.get("to")).append("<br/>");
        response.append("template : ").append(parms.get("template")).append("<br/>");
        response.append("</body></html>");
        writeResponse(httpExchange, response.toString());
    }

    private void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> queryToMap(String query) {
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

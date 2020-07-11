package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protobufmodels.LoginRequest.LoginRequset;
import server.ClientInstance;
import server.ManagerMain;
import utils.AuthenticationUtils;
import utils.CodingUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {
    Logger logger = LogManager.getLogger();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Login request received");
        logger.debug("Login request received");
        InputStream is = httpExchange.getRequestBody();
        String contentLength = "";
        int length = 0;
        try {
            contentLength = httpExchange.getRequestHeaders().getFirst("Content-Length");
            length = Integer.parseInt(contentLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = "<font color='#ff0000'>come on baby hhhhhdjkjjdddd!</font>";

        byte[] respContent = response.getBytes(StandardCharsets.UTF_8);

        httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        httpExchange.sendResponseHeaders(200, respContent.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(respContent);

        httpExchange.close();
    }
}

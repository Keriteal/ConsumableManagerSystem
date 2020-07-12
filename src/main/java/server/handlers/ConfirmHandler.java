package server.handlers;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConfirmHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void handle(HttpExchange t) throws IOException {
        try (InputStream is = t.getRequestBody(); OutputStream os = t.getResponseBody()) {

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            t.close();
        }
    }

    private void handleJson(JSONObject jsonObject) {
        try{

        }catch (JSONException jsonException) {
            logger.error(jsonException.getLocalizedMessage());
        }
    }
}

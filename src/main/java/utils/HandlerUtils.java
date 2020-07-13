package utils;

import com.sun.net.httpserver.HttpExchange;
import consts.HttpHeader;
import exceptions.handler.LengthRequiredException;
import server.ManagerMain;

import java.net.URI;

public class HandlerUtils {
    public enum ContentType {
        PROTOBUF, JSON, INVALID
    }

    public static ContentType getContentType(HttpExchange httpExchange) {
        String ContentType = httpExchange.getRequestHeaders().getFirst(HttpHeader.CONTENT_TYPE);
        HandlerUtils.ContentType type = HandlerUtils.ContentType.INVALID;

        if (ContentType.contains(ManagerMain.HTTP_CONTENT_TYPE_PROTOBUF)) {
            type = HandlerUtils.ContentType.PROTOBUF;
        } else if (ContentType.contains(ManagerMain.HTTP_CONTENT_TYPE_JSON)) {
            type = HandlerUtils.ContentType.JSON;
        }

        return type;
    }

    public static int getContentLength(HttpExchange httpExchange) throws LengthRequiredException, NumberFormatException {
        int len = 0;
        try {
            String length = httpExchange.getRequestHeaders().getFirst(HttpHeader.CONTENT_LENGTH);
            len = Integer.parseInt(length);
            return len;
        } catch (NumberFormatException e) {
            if (e.getMessage().contains("null")) {
                throw new LengthRequiredException();
            } else {
                throw e;
            }
        }
    }

    public static String getCommand(URI uri) {
        String url = uri.toString();
        return url.substring(url.indexOf('/'));
    }
}

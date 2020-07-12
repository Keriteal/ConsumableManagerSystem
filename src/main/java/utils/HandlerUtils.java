package utils;

import com.sun.net.httpserver.HttpExchange;
import consts.HttpHeader;
import server.ManagerMain;

public class HandlerUtils {
    public enum ContentType {
        PROTOBUF, JSON, INVALID
    }

    public static ContentType getContentType(HttpExchange httpExchange) {
        String ContentType = httpExchange.getRequestHeaders().getFirst(HttpHeader.CONTENT_TYPE);
        HandlerUtils.ContentType type = HandlerUtils.ContentType.INVALID;

        if(ContentType.contains(ManagerMain.HTTP_CONTENT_TYPE_PROTOBUF)) {
            type = HandlerUtils.ContentType.PROTOBUF;
        } else if(ContentType.contains(ManagerMain.HTTP_CONTENT_TYPE_JSON)) {
            type = HandlerUtils.ContentType.JSON;
        }

        return type;
    }

    public static int getContentLength(HttpExchange httpExchange) {

    }
}

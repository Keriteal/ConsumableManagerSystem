package server.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import exceptions.ParamException;
import exceptions.register.InvalidPasswordException;
import model.protobuf.RegisterProto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.AuthenticationUtils;
import utils.CodingUtils;
import utils.HandlerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegisterHandler implements HttpHandler {
    Logger logger = LogManager.getLogger();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);
        if (contentType == HandlerUtils.ContentType.INVALID) {
            //不支持的类型
            httpExchange.sendResponseHeaders(HttpStatusCode.NOT_ACCEPTABLE, 0);
            httpExchange.close();
            return;
        }
        InputStream is = httpExchange.getRequestBody();
        OutputStream os = httpExchange.getResponseBody();
        RegisterProto.RegisterRequest requestProto;
        RegisterProto.RegisterResponse responseProto;
        byte[] requestBytes, responseBytes;

        try {
            if(contentType == HandlerUtils.ContentType.JSON) {
                requestProto = jsonToProto()
            }
        } catch (JSONException jsonException) {

        } catch (ParamException paramException) {

        } catch (InvalidPasswordException e) {

        }
    }

    private RegisterProto.RegisterRequest jsonToProto(byte[] requestBytes)
            throws JSONException, ParamException, InvalidPasswordException {
        String jsonStr = new String(requestBytes, StandardCharsets.UTF_8);
        jsonStr = jsonStr.toLowerCase();
        logger.debug("接收到注册信息：");
        logger.debug(jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        String uuid = jsonObject.getString("uuid");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String contact = jsonObject.getString("contact");
        if (username == null || password == null) {
            throw new ParamException();
        }
        if (AuthenticationUtils.isValidPassword(password)) {
            throw new InvalidPasswordException();
        }
        if (contact == null) {
            contact = "";
        }
        RegisterProto.RegisterRequest.Builder builder = RegisterProto.RegisterRequest.newBuilder();
        builder.setUsername(username).setPassword(password).setContact(contact).setUuid(uuid);
        return builder.build();
    }

    private int getContentLength()
}

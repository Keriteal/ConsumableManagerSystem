package server.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.UserDAO;
import exceptions.LoginFailedException;
import model.UserBean;
import model.protobuf.Login.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;
import server.ManagerMain;
import utils.AuthenticationUtils;
import utils.CodingUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        logger.debug("Login request received");
        String contentType = httpExchange.getRequestHeaders().getFirst("Content-Type");
        String accepted = httpExchange.getRequestHeaders().getFirst("Accept");

        logger.debug("Received request in login: Content-Type:" + contentType);
        logger.debug("Accept:" + accepted);

        if (contentType.contains(ManagerMain.HTTP_CONTENT_TYPE_PROTOBUF)) {
            //Protobuf协议
            handleProtobuf(httpExchange);
        } else if (contentType.contains(ManagerMain.HTTP_CONTENT_TYPE_JSON)) {
            //Json协议
            handleJson(httpExchange);
        } else {
            httpExchange.sendResponseHeaders(406, 0);
        }
    }

    private void handleJson(HttpExchange t) throws IOException {
        JSONObject requestJson, responseJson;
        byte[] requestBytes, responseBytes;
        t.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        // 接收json，响应json
        try (InputStream is = t.getRequestBody(); OutputStream os = t.getResponseBody()) {
            String contentLength = t.getRequestHeaders().getFirst("Content-Length");
            int length = Integer.parseInt(contentLength); //获取长度
            byte[] requestData = CodingUtils.streamToByteArray(is, length);
            if (requestData != null) {
                String requestStr = new String(requestData, StandardCharsets.UTF_8);
                logger.debug(requestStr);
                //解析json
                requestJson = JSON.parseObject(requestStr);
                String sUuid = requestJson.getString("UUID");
                int userId = requestJson.getIntValue("UserID");
                String password = requestJson.getString("Password");
                logger.debug("Parsed data: UUID=" + sUuid + ",UserID=" + userId + ",Password=" + password);
                //构建LoginRequest
                LoginRequest.Builder builder = LoginRequest.newBuilder();
                builder.setId(userId);
                builder.setUuid(sUuid);
                builder.setPassword(password);
                //验证LoginRequest
                ClientInstance instance = AuthenticationUtils.login(builder.build());
                ManagerMain.clientInstanceMap.put(sUuid, instance);
                logger.debug("Map = " + ManagerMain.clientInstanceMap);
                UserBean user = new UserDAO().getUserBean(instance.getId());
                //构建Response
            }
        } catch (JSONException jsonException) {
            //json解析错误
            logger.error("Error when parsing json " + jsonException.getMessage());
            t.sendResponseHeaders(406, 0);
        } catch (LoginFailedException e) {
            //登录错误
            logger.error("Login failed");
            t.sendResponseHeaders(400, 0);
        } catch (Exception e) {
            //其他错误
            logger.error(e.getMessage());
            t.sendResponseHeaders(500, 0);
        } finally {
            t.close();
        }
    }

    private void handleProtobuf(HttpExchange t) throws IOException {
        t.getResponseHeaders().add("Content-Type", "application/x-protobuf");
        InputStream is = t.getRequestBody();
        OutputStream os = t.getResponseBody();

        int length = 0;
        byte[] respData;
        try {
            //解析请求头
            String contentLength = t.getRequestHeaders().getFirst("Content-Length");
            length = Integer.parseInt(contentLength);
            logger.debug("Length:" + contentLength);
            //获取请求的数据
            byte[] requestData = CodingUtils.streamToByteArray(is, length);
            //解析
            LoginRequest loginRequest = LoginRequest.parseFrom(requestData);
            String UUID = loginRequest.getUuid();
            //处理
            ClientInstance instance = AuthenticationUtils.login(loginRequest);
            logger.debug(UUID);
            ManagerMain.clientInstanceMap.put(UUID, instance);
        } catch (LoginFailedException f) {
            //登录失败
            LoginResponse.Builder response = LoginResponse.newBuilder();
            response.setResult(LoginResponse.Result.FAILED);
            respData = response.build().toByteArray();

            t.sendResponseHeaders(200, respData.length);
            os.write(respData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            t.sendResponseHeaders(400, 0);
        } finally {
            is.close();
            os.close();
            t.close();
        }
    }
}

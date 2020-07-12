package server.handlers;

import consts.HttpStatusCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.UserDAO;
import exceptions.NoSuchUserException;
import exceptions.PasswordWrongException;
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
import java.security.NoSuchAlgorithmException;

public class LoginHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDAO userdao = new UserDAO();

    @Override
    public void handle(HttpExchange t) throws IOException {
        String contentType = t.getRequestHeaders().getFirst("Content-Type");
        String accepted = t.getRequestHeaders().getFirst("Accept");
        int length;
        try {
            String lengthStr = t.getRequestHeaders().getFirst("Content-Length");
            length = Integer.parseInt(lengthStr);
        } catch (Exception ignored) {
            logger.error("接收到了错误的 Content-Length");
            t.sendResponseHeaders(411, 0);
            t.close();
            return;
        }

        logger.debug("接收到登录请求: Content-Type=" + contentType + ";Accept=" + accepted + ";Content-Length=" + length);

        byte[] responseBytes;
        LoginResponse.Builder responseProtoBuilder = LoginResponse.newBuilder();
        OutputStream os = t.getResponseBody();
        boolean isJson = true;

        try (InputStream is = t.getRequestBody()) {
            //处理请求
            LoginRequest requestProto;
            responseProtoBuilder = LoginResponse.newBuilder();
            byte[] requestBytes = CodingUtils.streamToByteArray(is, length);
            if (requestBytes == null) {
                t.sendResponseHeaders(400, 0);
                t.close();
                return;
            }
            //获取传输类型
            if (contentType.contains(ManagerMain.HTTP_CONTENT_TYPE_PROTOBUF)) {
                isJson = false;
                requestProto = LoginRequest.parseFrom(requestBytes);
            } else if (contentType.contains(ManagerMain.HTTP_CONTENT_TYPE_JSON)) {
                String jsonStr = new String(requestBytes, StandardCharsets.UTF_8);
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                requestProto = parseJson(jsonObject);
            } else {
                //不支持的类型
                t.sendResponseHeaders(HttpStatusCode.NOT_ACCEPTABLE, 0);
                return;
            }
            //验证用户信息
            UserBean userBean = userdao.getUserBean(requestProto.getUsername());
            if (userBean.getPassword().equals(requestProto.getPassword())) {
                //密码正确
                responseProtoBuilder.setResult(LoginResponse.Result.SUCCESS);
                String secret = AuthenticationUtils.generateSecret(128);
                logger.debug("为用户" + userBean.getName() + "生成密钥：" + secret);
                ManagerMain.clientInstanceMap.put(requestProto.getUuid(),
                        new ClientInstance(userBean.getId(), requestProto.getUserType(), secret));
                responseProtoBuilder.setSecret(secret);
            } else {
                throw new PasswordWrongException(userBean.getName());
            }
            if (isJson) {
                JSONObject response = parseResponse(responseProtoBuilder.build());
                responseBytes = response.toJSONString().getBytes(StandardCharsets.UTF_8);
            } else {
                responseBytes = responseProtoBuilder.build().toByteArray();
            }
            //发送结果
            t.sendResponseHeaders(HttpStatusCode.HTTP_OK, responseBytes.length);
            os.write(responseBytes);
        } catch (NoSuchUserException noSuchUserException) {
            logger.debug(noSuchUserException.getMessage());
            responseProtoBuilder.setResult(LoginResponse.Result.NO_USER);
            responseBytes = responseProtoBuilder.build().toByteArray();
            t.sendResponseHeaders(HttpStatusCode.HTTP_OK, responseBytes.length);
            os.write(responseBytes);
        } catch (PasswordWrongException wrongException) {
            responseProtoBuilder.setResult(LoginResponse.Result.PASSWORD_WRONG);
            responseBytes = responseProtoBuilder.build().toByteArray();
            t.sendResponseHeaders(HttpStatusCode.HTTP_OK, responseBytes.length);
            os.write(responseBytes);
        } catch (NoSuchAlgorithmException exception) {
            logger.fatal("AES NOT Exists");
            t.sendResponseHeaders(HttpStatusCode.INTERNAL_SERVER_ERROR, 0);
        } catch (JSONException jsonException) {
            logger.error("Invalid Json");
        } catch (NullPointerException nullPointerException) {
            logger.error(nullPointerException.getLocalizedMessage());
            nullPointerException.printStackTrace();
        } finally {
            os.close();
            t.close();
        }
    }

    private LoginRequest parseJson(JSONObject jsonObject) throws JSONException, NullPointerException {
        LoginRequest.Builder builder = LoginRequest.newBuilder();
        try {
            builder.setUuid(jsonObject.getString("uuid"));
            builder.setPassword(jsonObject.getString("password"));
            builder.setUsername(jsonObject.getString("user_name"));
            if (jsonObject.getString("user_type").equals("admin")) {
                builder.setUserType(LoginRequest.UserType.ADMIN);
            } else {
                builder.setUserType(LoginRequest.UserType.USER);
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            throw new JSONException("Null Pointer");
        }
        return builder.build();
    }

    private JSONObject parseResponse(LoginResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", response.getResult().getNumber());
        jsonObject.put("secret", response.getSecret());
        return jsonObject;
    }
}

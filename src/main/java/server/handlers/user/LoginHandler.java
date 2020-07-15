package server.handlers.user;

import consts.HttpStatusCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAO;
import dao.UserDAO;
import exceptions.NoSuchUserException;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.AdminBean;
import model.UserBean;
import model.protobuf.Login.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;
import server.ManagerMain;
import utils.AuthenticationUtils;
import utils.CodingUtils;
import utils.HandlerUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * uuid, user_name, password, user_type
 */
public class LoginHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDAO userDao = new UserDAO();
    private static final AdminDAO adminDao = new AdminDAO();

    @Override
    public void handle(HttpExchange t) throws IOException {
        logger.debug("=======Login=======");
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(t);
        LoginResponse resp;
        LoginRequest req;
        byte[] respData, reqData;

        try {
            int length = HandlerUtils.getContentLength(t);
            reqData = CodingUtils.streamToByteArray(t.getRequestBody(), length);
            if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                req = LoginRequest.parseFrom(reqData);
            } else if (contentType == HandlerUtils.ContentType.JSON) {
                req = jsonToProto(reqData);
            } else {
                throw new ContentTypeException();
            }
            resp = getResponse(req);
            Response(t, resp, contentType);
        } catch (LengthRequiredException e) {
            t.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED, 0);
        } catch (MissingParamException e) {
            t.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("缺少参数");
        } catch (ContentTypeException e) {
            t.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("ContentType不支持");
        } catch (NoSuchAlgorithmException e) {
            t.sendResponseHeaders(HttpStatusCode.INTERNAL_SERVER_ERROR, 0);
            e.printStackTrace();
        } finally {
            t.close();
        }
        logger.debug("===================");
    }

    private LoginResponse getResponse(LoginRequest request)
            throws NoSuchAlgorithmException {
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        String uuid = request.getUuid();
        logger.debug("uuid:" + uuid);
        String password = request.getPassword();
        logger.debug("password:" + password);
        String username = request.getUsername();
        logger.debug("username" + username);
        String secret = null;
        int id;
        try {
            builder.setResult(LoginResponse.Result.SUCCESS);
            if (request.getUserType() == LoginRequest.UserType.ADMIN) {
                //管理员登录
                AdminBean bean = adminDao.query(username);
                id = bean.getId();
                if (bean.getPassword().equals(password)) {
                    //密码正确
                    secret = AuthenticationUtils.generateSecret(128);
                    builder.setResult(LoginResponse.Result.SUCCESS);
                } else {
                    //错误
                    builder.setResult(LoginResponse.Result.PASSWORD_WRONG);
                }
            } else {
                //用户登录
                UserBean bean = userDao.query(username);
                id = bean.getId();
                if (bean.getPassword().equals(password)) {
                    //密码正确
                    secret = AuthenticationUtils.generateSecret(128);
                    builder.setResult(LoginResponse.Result.SUCCESS);
                } else {
                    //错误
                    builder.setResult(LoginResponse.Result.PASSWORD_WRONG);
                }
            }
            if (secret != null) {
                ClientInstance instance = new ClientInstance(id, request.getUserType(), secret);
                ManagerMain.clientInstanceMap.put(uuid, instance);
                builder.setSecret(secret);
            }
            logger.debug("生成密钥：" + secret);
        } catch (NoSuchUserException e) {
            builder.setResult(LoginResponse.Result.NO_USER);
        }
        return builder.build();
    }

    private LoginRequest jsonToProto(byte[] bytes)
            throws MissingParamException {
        String str = new String(bytes, StandardCharsets.UTF_8);
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);
        LoginRequest.Builder builder = LoginRequest.newBuilder();
        String uuid = jsonObject.getString("uuid");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String userType = jsonObject.getString("usertype");

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new MissingParamException();
        }
        if (userType.equals("admin")) {
            builder.setUserType(LoginRequest.UserType.ADMIN);
        } else {
            builder.setUserType(LoginRequest.UserType.USER);
        }
        return builder
                .setUuid(uuid)
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    private byte[] protoToJson(LoginResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", response.getResultValue());
        jsonObject.put("result", response.getResult());
        jsonObject.put("secret", response.getSecret());
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private void Response(HttpExchange t, LoginResponse resp, HandlerUtils.ContentType contentType) throws IOException {
        byte[] respData;
        if (contentType == HandlerUtils.ContentType.JSON) {
            respData = protoToJson(resp);
        } else {
            respData = resp.toByteArray();
        }
        t.sendResponseHeaders(HttpStatusCode.HTTP_OK, respData.length);
        t.getResponseBody().write(respData);
    }
}

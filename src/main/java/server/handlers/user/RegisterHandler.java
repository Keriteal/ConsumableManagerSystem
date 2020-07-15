package server.handlers.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.UserDAO;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import exceptions.register.AlreadyHasUserException;
import exceptions.register.InvalidPasswordException;
import model.UserBean;
import model.protobuf.RegisterProto;
import org.apache.commons.lang.StringUtils;
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
        RegisterProto.RegisterResponse.Builder builder = RegisterProto.RegisterResponse.newBuilder();
        byte[] requestBytes, responseBytes;

        try {
            int contentLength = HandlerUtils.getContentLength(httpExchange);
            requestBytes = CodingUtils.streamToByteArray(is, contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                requestProto = jsonToProto(requestBytes);
            } else {
                requestProto = RegisterProto.RegisterRequest.parseFrom(requestBytes);
            }
            responseProto = register(requestProto);
            if (responseProto == null) {
                httpExchange.sendResponseHeaders(HttpStatusCode.INTERNAL_SERVER_ERROR, 0);
                return;
            }
            //构建响应字节数组
            if (contentType == HandlerUtils.ContentType.JSON) {
                responseBytes = protoToJson(responseProto);
            } else {
                responseBytes = responseProto.toByteArray();
            }
            httpExchange.sendResponseHeaders(HttpStatusCode.HTTP_OK, responseBytes.length);
            os.write(responseBytes);
        } catch (JSONException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.NOT_ACCEPTABLE, 0);
        } catch (MissingParamException missingParamException) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
        } catch (InvalidPasswordException e) {
            builder.setResult(RegisterProto.RegisterResponse.Result.INVALID_USERNAME);
            responseBytes = builder.build().toByteArray();
            httpExchange.sendResponseHeaders(HttpStatusCode.HTTP_OK, responseBytes.length);
            os.write(responseBytes);
        } catch (LengthRequiredException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED, 0);
        } finally {
            is.close();
            os.close();
            httpExchange.close();
        }
    }

    /**
     * @param requestBytes 请求的字节信息
     * @return model.protobuf.RegisterProto.RegisterRequest 请求模型
     * @author keriteal
     **/
    private RegisterProto.RegisterRequest jsonToProto(byte[] requestBytes)
            throws JSONException, MissingParamException, InvalidPasswordException {
        String jsonStr = new String(requestBytes, StandardCharsets.UTF_8);
        jsonStr = jsonStr.toLowerCase();
        logger.debug("接收到注册信息：");
        logger.debug(jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        String uuid = jsonObject.getString("uuid");
        String username = jsonObject.getString("username");
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        String contact = jsonObject.getString("contact");
        if (!StringUtils.isNotBlank(username) || !StringUtils.isNotBlank(password) || !StringUtils.isNotBlank(name)) {
            throw new MissingParamException();
        }
        if (!AuthenticationUtils.IsValidPassword(password)) {
            throw new InvalidPasswordException();
        }
        if (contact == null) {
            contact = "";
        }
        RegisterProto.RegisterRequest.Builder builder = RegisterProto.RegisterRequest.newBuilder();
        builder.setUsername(username).setPassword(password).setContact(contact).setUuid(uuid);
        return builder.build();
    }

    private byte[] protoToJson(RegisterProto.RegisterResponse response) {
        JSONObject json = new JSONObject();
        json.put("result", response.getResultValue());
        return json.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private RegisterProto.RegisterResponse register(RegisterProto.RegisterRequest request) {
        RegisterProto.RegisterResponse.Builder builder = RegisterProto.RegisterResponse.newBuilder();;
        UserDAO userDAO = new UserDAO();
        UserBean user = new UserBean();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setContact(request.getContact());
        try {
            userDAO.insert(user);
            builder.setResult(RegisterProto.RegisterResponse.Result.SUCCESS);
            return builder.build();
        } catch (AlreadyHasUserException e) {
            builder.setResult(RegisterProto.RegisterResponse.Result.REPEAT);
        } catch (Exception e) {
            logger.debug(e);
        }
        return null;
    }
}

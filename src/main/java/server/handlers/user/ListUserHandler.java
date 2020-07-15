package server.handlers.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.UserDAO;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.UserBean;
import model.protobuf.UserProto.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;
import server.ManagerMain;
import utils.CodingUtils;
import utils.HandlerUtils;
import utils.ProtobufUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//List
public class ListUserHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDAO dao = new UserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        logger.debug("=======Handle List User=======");
        HandlerUtils.ContentType type = HandlerUtils.getContentType(httpExchange);
        byte[] req;
        UserRequest request;
        UserResponse response;

        try{
            int length = HandlerUtils.getContentLength(httpExchange);
            req = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), length);
            if(type == HandlerUtils.ContentType.JSON){
                request = jsonToProto(req);
            } else if(type == HandlerUtils.ContentType.PROTOBUF){
                request = UserRequest.parseFrom(req);
            }else {
                throw new ContentTypeException();
            }
            response = getResponse(request);
            Response(httpExchange, response, type);
        } catch (LengthRequiredException e){
            httpExchange.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED, 0);
            logger.error("Content Length");
        } catch (ContentTypeException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST,0);
            logger.error("Content Type");
        } catch (MissingParamException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.error("缺少参数");
        } finally {
            logger.debug("=========================");
            httpExchange.close();
        }
    }

    private UserRequest jsonToProto(byte[] data)
            throws MissingParamException {
        logger.debug("Required Json: uuid, str, type");
        String str = new String(data, StandardCharsets.UTF_8);
        logger.debug("str");
        UserRequest.Builder builder = UserRequest.newBuilder();
        JSONObject jsonObject = JSON.parseObject(str);
        String uuid = jsonObject.getString("uuid");
        logger.debug("uuid:" + uuid);
        String secret = jsonObject.getString("secret");
        logger.debug("secret:" + secret);
        int type = jsonObject.getInteger("type");
        logger.debug("type:" + type);
        JSONObject param = jsonObject.getJSONObject("params");
        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }
        if (param == null) {
            throw new MissingParamException();
        }
        UserParams.Builder paramBuilder = UserParams.newBuilder();
        int user_id = param.getInteger("id");
        String user_name = param.getString("name");
        String user_contact = param.getString("contact");
        paramBuilder
                .setUserId(user_id)
                .setUserName(user_name)
                .setUserContact(user_contact);
        builder
                .setUuid(uuid)
                .setSecret(secret)
                .setParams(paramBuilder);
        return builder.build();
    }

    private byte[] protoToJson(UserResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", response.getResult());
        jsonObject.put("code", response.getResultValue());
        JSONArray array = new JSONArray();
        for (UserParams info : response.getUserListList()) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", info.getUserId());
            tmp.put("name", info.getUserName());
            tmp.put("contact", info.getUserContact());
            tmp.put("time_login", info.getUserLatestLoginTime());
            tmp.put("time_register", info.getUserRegisterTime());
            array.add(tmp);
        }
        jsonObject.put("list", array);
        String str = jsonObject.toJSONString();
        logger.debug("Response: " + str);
        return str.getBytes(StandardCharsets.UTF_8);
    }

    private UserResponse getResponse(UserRequest request) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        ClientInstance ci = ManagerMain.clientInstanceMap.get(request.getUuid());
        String secret = ci.getSecret();
        if(!secret.equals(request.getSecret())){
            builder.setResult(UserResponse.Result.PERMISSION_DENIED);
        } else {
            UserParams.Builder param = UserParams.newBuilder();
            for(UserBean bean: dao.list()){
                param.clear()
                        .setUserId(bean.getId())
                        .setUserName(bean.getName())
                        .setUserContact(bean.getContact())
                        .setUserRegisterTime(
                                ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getRegisterTime())
                        )
                        .setUserLatestLoginTime(
                                ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getLatestLogin())
                        );
                builder.addUserList(param.build());
            }
            builder.setResult(UserResponse.Result.SUCCESS);
        }
        return builder.build();
    }

    private void Response(HttpExchange t, UserResponse response, HandlerUtils.ContentType contentType)
            throws IOException {
        byte[] resp;
        if (contentType == HandlerUtils.ContentType.JSON) {
            resp = protoToJson(response);
        } else {
            resp = response.toByteArray();
        }
        t.sendResponseHeaders(HttpStatusCode.HTTP_OK, resp.length);
        t.getResponseBody().write(resp);
    }
}

package server.handlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.AdminDAO;
import exceptions.authentication.NotLoginException;
import exceptions.authentication.SecretWrongException;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.AdminBean;
import model.protobuf.AdminProto.*;
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

public class ListAdminHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final AdminDAO dao = new AdminDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);
        byte[] reqData, respData;
        AdminRequest req;
        AdminResponse resp;
        try {
            int contentLength = HandlerUtils.getContentLength(httpExchange);
            reqData = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                req = jsonToProto(reqData);
            } else if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                req = AdminRequest.parseFrom(reqData);
            } else {
                throw new ContentTypeException();
            }
            resp = getResponse(req);
            Response(httpExchange, resp, contentType);
        } catch (LengthRequiredException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED, 0);
            logger.debug("缺少长度参数");
        } catch (ContentTypeException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("Content-Type Wrong");
        } catch (MissingParamException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("缺少参数");
        } catch (NotLoginException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.UNAUTHORIZED, 0);
            logger.debug("未登录");
        } catch (SecretWrongException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.FORBIDDEN, 0);
            logger.debug("密钥错误");
        } finally {
            httpExchange.close();
        }
    }

    private AdminRequest jsonToProto(byte[] bytes)
            throws MissingParamException {
        String str = new String(bytes, StandardCharsets.UTF_8);
        logger.debug(str);
        JSONObject jsonObject = JSONObject.parseObject(str);
        AdminRequest.Builder builder = AdminRequest.newBuilder();

        String uuid = jsonObject.getString("uuid");
        String secret = jsonObject.getString("secret");

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }
        builder.setUuid(uuid).setSecret(secret);
        return builder.build();
    }

    private byte[] protoToJson(AdminResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", response.getResultValue());
        jsonObject.put("result_description", response.getResult());

        JSONArray array = new JSONArray();
        for (UserInfo info : response.getAdminListList()) {
            JSONObject object = new JSONObject();
            object.put("id", info.getAdminId());
            object.put("name", info.getAdminName());
            object.put("contact", info.getAdminContact());
            object.put("time_login", info.getUserLatestLoginTime().getSeconds());
            array.add(object);
        }
        jsonObject.put("admins", array);
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private AdminResponse getResponse(AdminRequest request)
            throws SecretWrongException, NotLoginException {
        AdminResponse.Builder builder = AdminResponse.newBuilder();
        String uuid = request.getUuid();
        String secret = request.getSecret();
        ClientInstance clientInstance = ManagerMain.clientInstanceMap.get(uuid);
        if (clientInstance == null) {
            throw new NotLoginException();
        }
        if (!clientInstance.getSecret().equals(secret)) {
            throw new SecretWrongException();
        }
        builder.setResult(AdminResponse.Result.FAILED);
        UserInfo.Builder uib = UserInfo.newBuilder();
        for (AdminBean bean : dao.listAll()) {
            uib.setAdminId(bean.getId())
                    .setAdminName(bean.getName())
                    .setAdminContact(bean.getContact())
                    .setUserLatestLoginTime(ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getTimeLogin()));
            builder.addAdminList(uib.build());
            builder.setResult(AdminResponse.Result.SUCCESS);
        }
        return builder.build();
    }

    private void Response(HttpExchange t, AdminResponse response, HandlerUtils.ContentType contentType) throws IOException {
        byte[] respData;
        if (contentType == HandlerUtils.ContentType.JSON) {
            respData = protoToJson(response);
        } else {
            respData = response.toByteArray();
        }
        t.sendResponseHeaders(HttpStatusCode.HTTP_OK, respData.length);
        t.getResponseBody().write(respData);
    }
}

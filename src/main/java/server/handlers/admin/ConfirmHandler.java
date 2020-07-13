package server.handlers.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.RecordDAO;
import exceptions.authentication.NotLoginException;
import exceptions.authentication.PermissionException;
import exceptions.authentication.SecretWrongException;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.RecordBean;
import model.protobuf.ConfirmProto.*;
import model.protobuf.Login;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static consts.HttpStatusCode.*;

import server.ClientInstance;
import server.ManagerMain;
import utils.CodingUtils;
import utils.HandlerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ConfirmHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final RecordDAO dao = new RecordDAO();

    @Override
    public void handle(HttpExchange t) throws IOException {
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(t);
        byte[] reqBytes, respBytes;
        ConfirmRequest req;
        ConfirmResponse resp;

        try {
            InputStream is = t.getRequestBody();
            int contentLength = HandlerUtils.getContentLength(t);
            reqBytes = CodingUtils.streamToByteArray(is, contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                logger.debug("接收到 Json");
                req = jsonToProto(reqBytes);
            } else if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                logger.debug("接收到 Protobuf");
                req = ConfirmRequest.parseFrom(reqBytes);
            } else {
                throw new ContentTypeException();
            }
            resp = getResponse(req);
            sendResponse(t, resp, contentType);
        } catch (ContentTypeException e) {
            t.sendResponseHeaders(BAD_REQUEST, 0);
            logger.debug("Content-Type 错误");
        } catch (LengthRequiredException e) {
            t.sendResponseHeaders(LENGTH_REQUIRED, 0);
            logger.debug("长度错误");
        } catch (MissingParamException e) {
            t.sendResponseHeaders(BAD_REQUEST, 0);
            logger.debug("参数错误");
        } catch (PermissionException e) {
            t.sendResponseHeaders(UNAUTHORIZED, 0);
            logger.debug("权限不足");
        } catch (NotLoginException e) {
            t.sendResponseHeaders(UNAUTHORIZED, 0);
            logger.debug("未登录");
        } catch (SecretWrongException e) {
            t.sendResponseHeaders(FORBIDDEN, 0);
            logger.debug("密钥错误");
        } finally {
            t.close();
        }
    }

    private ConfirmResponse getResponse(ConfirmRequest request)
            throws NotLoginException, SecretWrongException, PermissionException {
        ConfirmResponse.Builder builder = ConfirmResponse.newBuilder();
        String uuid = request.getUuid();
        int cr_id = request.getRecordId();
        String secret = request.getSecret();
        ClientInstance ci = ManagerMain.clientInstanceMap.get(uuid);

        if (ci == null) {
            throw new NotLoginException();
        }
        if (!ci.getSecret().equals(secret)) {
            throw new SecretWrongException();
        }
        if (ci.getUserType() == Login.LoginRequest.UserType.USER) {
            throw new PermissionException();
        }
        RecordBean record = new RecordBean();
        record.setConsumableItemId(cr_id);
        record.setAdminUser(ci.getId());

        if (dao.confirm(record)) {
            builder.setResult(ConfirmResponse.Result.SUCCESS);
        } else {
            builder.setResult(ConfirmResponse.Result.FAILED);
        }
        return builder.build();
    }

    private void sendResponse(HttpExchange t, ConfirmResponse resp, HandlerUtils.ContentType contentType) throws IOException {
        byte[] respBytes;
        if (contentType == HandlerUtils.ContentType.JSON) {
            respBytes = protoToJson(resp);
        } else {
            respBytes = resp.toByteArray();
        }
        t.sendResponseHeaders(HTTP_OK, respBytes.length);
        t.close();
    }

    private byte[] protoToJson(ConfirmResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", response.getResultValue());
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private ConfirmRequest jsonToProto(byte[] bytes)
            throws MissingParamException {
        ConfirmRequest.Builder builder = ConfirmRequest.newBuilder();
        String str = new String(bytes, StandardCharsets.UTF_8).toLowerCase();
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);

        String uuid = jsonObject.getString("uuid");
        int cr_id = jsonObject.getInteger("record_id");
        String secret = jsonObject.getString("secret");

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }

        builder.setUuid(uuid);
        builder.setRecordId(cr_id);
        builder.setSecret(secret);
        return builder.build();
    }
}

package server.handlers.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.RecordDAO;
import exceptions.authentication.NotLoginException;
import exceptions.authentication.SecretWrongException;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.RecordBean;
import model.protobuf.CommitProto.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientInstance;
import server.ManagerMain;
import utils.CodingUtils;
import utils.HandlerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CommitHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final RecordDAO dao = new RecordDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);
        CommitRequest req;
        CommitResponse resp;
        byte[] reqBytes;
        try {
            InputStream is = httpExchange.getRequestBody();
            //获取请求数据
            int contentLength = HandlerUtils.getContentLength(httpExchange);
            reqBytes = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                logger.debug("接收到Json格式的数据：");
                req = jsonToProto(reqBytes);
            } else if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                logger.debug("接收到Protobuf数据");
                req = CommitRequest.parseFrom(reqBytes);
            } else {
                throw new ContentTypeException();
            }
            resp = getResponse(req);
            logger.debug("开始响应");
            Response(httpExchange, resp, contentType);
        } catch (LengthRequiredException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED, 0);
            logger.debug("Content-Length未指定");
        } catch (MissingParamException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("错误的参数");
        } catch (ContentTypeException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("错误的 Content-Type");
        } catch (NotLoginException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.UNAUTHORIZED, 0);
            logger.debug("未登录::" + e.getMessage());
        } catch (SecretWrongException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.FORBIDDEN, 0);
            logger.debug("密钥错误::" + e.getMessage());
        } finally {
            httpExchange.close();
        }
    }

    private CommitRequest jsonToProto(byte[] bytes)
            throws MissingParamException {
        String str = new String(bytes, StandardCharsets.UTF_8);
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);
        CommitRequest.Builder builder = CommitRequest.newBuilder();

        String uuid = jsonObject.getString("uuid");
        Integer ci_id = jsonObject.getInteger("consumable_id");
        String secret = jsonObject.getString("secret");

        if (StringUtils.isBlank(uuid) || ci_id == null || ci_id == 0 || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }
        builder.setUuid(uuid);
        builder.setConsumableId(ci_id);
        builder.setSecret(secret);
        return builder.build();
    }

    private byte[] protoToJson(CommitResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", response.getResultValue());
        jsonObject.put("result_description", response.getResult().toString());
        return jsonObject.toJSONString().getBytes();
    }

    private CommitResponse getResponse(CommitRequest request)
            throws NotLoginException, SecretWrongException {
        CommitResponse.Builder builder = CommitResponse.newBuilder();
        String uuid = request.getUuid();
        int ci_id = request.getConsumableId();
        String secret = request.getSecret();
        ClientInstance ci = ManagerMain.clientInstanceMap.get(uuid);
        if (ci == null) {
            throw new NotLoginException();
        }
        if (!ci.getSecret().equals(secret)) {
            throw new SecretWrongException();
        }
        RecordBean record = new RecordBean();
        record.setApplicationUser(ci.getId()); //申请人
        record.setConsumableItemId(request.getConsumableId());//申请的物品
        if (!dao.commit(record)) {
            builder.setResult(CommitResponse.Result.FAILED);
        } else {
            builder.setResult(CommitResponse.Result.SUCCESS);
        }
        return builder.build();
    }

    private void Response(HttpExchange t, CommitResponse resp, HandlerUtils.ContentType contentType) throws IOException {
        byte[] respBytes;
        if (contentType == HandlerUtils.ContentType.JSON) {
            respBytes = protoToJson(resp);
        } else {
            respBytes = resp.toByteArray();
        }
        t.sendResponseHeaders(HttpStatusCode.HTTP_OK, respBytes.length);
        t.getResponseBody().write(respBytes);
    }
}

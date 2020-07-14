package server.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.ConsumableDAO;
import exceptions.authentication.NotLoginException;
import exceptions.authentication.SecretWrongException;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.ConsumableBean;
import model.protobuf.ConsumableProto.*;
import model.protobuf.Login;
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

// 对耗材的增改查
public class ItemsHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final ConsumableDAO dao = new ConsumableDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);
        byte[] reqData, respData;
        ConsumableRequest req;
        ConsumableResponse resp;

        try {
            int contentLength = HandlerUtils.getContentLength(httpExchange);
            reqData = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                req = jsonToRequest(reqData);
            } else if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                req = ConsumableRequest.parseFrom(reqData);
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

    private byte[] protoToJson(ConsumableResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", response.getCodeValue());
        jsonObject.put("result_description", response.getCode());
        if (response.getResultList().size() == 0) {
            return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
        }
        JSONArray array = new JSONArray();
        for (Params info : response.getResultList()) {
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("item_id", info.getConsId());
            tmpJson.put("item_name", info.getConsName());
            tmpJson.put("item_stock", info.getConsStock());
            tmpJson.put("item_added", info.getConsAddedTime().getSeconds());
            tmpJson.put("item_modified", info.getConsModifiedTime().getSeconds());
            array.add(tmpJson);
        }
        jsonObject.put("items", array);
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private ConsumableRequest jsonToRequest(byte[] bytes)
            throws MissingParamException {
        String str = new String(bytes, StandardCharsets.UTF_8);
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);
        ConsumableRequest.Builder builder = ConsumableRequest.newBuilder();
        Params.Builder paramBuilder = Params.newBuilder();

        String uuid = jsonObject.getString("uuid");
        String secret = jsonObject.getString("secret");
        Integer type = jsonObject.getInteger("type");

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }
        if (type == null) {
            type = ConsumableRequest.RequestType.LIST_ALL_VALUE;
        }
        JSONObject paramObject = jsonObject.getJSONObject("item_params");
        if (paramObject == null && type != ConsumableRequest.RequestType.LIST_ALL_VALUE) {
            throw new MissingParamException();
        } else if (type != ConsumableRequest.RequestType.LIST_ALL_VALUE) {
            Integer item_id = paramObject.getInteger("id");
            String item_name = paramObject.getString("name");
            Integer item_stock = paramObject.getInteger("stock");
            paramBuilder.setConsId(item_id).setConsName(item_name).setConsStock(item_stock);
        }
        builder.setUuid(uuid).setTypeValue(type).setSecret(secret).setParam(paramBuilder.build());
        return builder.build();
    }

    private ConsumableResponse getResponse(ConsumableRequest request)
            throws NotLoginException, SecretWrongException {
        ConsumableResponse.Builder builder = ConsumableResponse.newBuilder();
        String uuid = request.getUuid();
        String secret = request.getSecret();
        Params param = request.getParam();
        ClientInstance ci = ManagerMain.clientInstanceMap.get(uuid);
        if (ci == null) {
            throw new NotLoginException();
        }
        if (!ci.getSecret().equals(secret)) {
            throw new SecretWrongException();
        }
        switch (request.getType()) {
            case ADD: {
                if (ci.getUserType() != Login.LoginRequest.UserType.ADMIN) {
                    break;
                }
                ConsumableBean bean = new ConsumableBean();
                bean.setName(param.getConsName());
                bean.setStock(param.getConsStock());
                if (dao.insert(bean)) {
                    builder.setCode(ConsumableResponse.Result.SUCCESS);
                } else {
                    builder.setCode(ConsumableResponse.Result.FAILED);
                }
                break;
            }
            case EDIT: {
                if (ci.getUserType() != Login.LoginRequest.UserType.ADMIN) {
                    break;
                }
                ConsumableBean bean = new ConsumableBean();
                bean.setId(param.getConsId());
                bean.setName(param.getConsName());
                bean.setStock(param.getConsStock());
                if (dao.edit(bean)) {
                    builder.setCode(ConsumableResponse.Result.SUCCESS);
                } else {
                    builder.setCode(ConsumableResponse.Result.FAILED);
                }
                break;
            }
            case LIST_ALL: {
                Params.Builder b = Params.newBuilder();
                for (ConsumableBean bean : dao.listAll()) {
                    b.setConsId(bean.getId())
                            .setConsName(bean.getName())
                            .setConsStock(bean.getStock())
                            .setConsAddedTime(ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getAddedTime()))
                            .setConsModifiedTime(ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getModifiedTime()));
                    builder.addResult(b.build());
                }
                break;
            }
            default: {
                break;
            }
        }
        return builder.build();
    }

    private void Response(HttpExchange t, ConsumableResponse resp, HandlerUtils.ContentType contentType)
            throws IOException {
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

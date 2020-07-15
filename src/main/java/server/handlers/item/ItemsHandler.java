package server.handlers.item;

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
import model.protobuf.ItemProto.*;
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
        logger.debug("=======Handle Item=======");
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);
        byte[] reqData, respData;
        ItemRequest req;
        ItemResponse resp;

        try {
            int contentLength = HandlerUtils.getContentLength(httpExchange);
            reqData = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), contentLength);
            if (contentType == HandlerUtils.ContentType.JSON) {
                req = jsonToRequest(reqData);
            } else if (contentType == HandlerUtils.ContentType.PROTOBUF) {
                req = ItemRequest.parseFrom(reqData);
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
            logger.debug("=========================");
            httpExchange.close();
        }
    }

    private byte[] protoToJson(ItemResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", response.getResultValue());
        jsonObject.put("result_description", response.getResult());
        if (response.getItemListList().size() == 0) {
            return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
        }
        JSONArray array = new JSONArray();
        for (ItemParams info : response.getItemListList()) {
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("item_id", info.getItemId());
            tmpJson.put("item_name", info.getItemName());
            tmpJson.put("item_stock", info.getItemStock());
            tmpJson.put("item_added", info.getItemAddedTime().getSeconds());
            tmpJson.put("item_modified", info.getItemModifiedTime().getSeconds());
            array.add(tmpJson);
        }
        jsonObject.put("items", array);
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private ItemRequest jsonToRequest(byte[] bytes)
            throws MissingParamException {
        String str = new String(bytes, StandardCharsets.UTF_8);
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);
        ItemRequest.Builder builder = ItemRequest.newBuilder();
        ItemParams.Builder paramBuilder = ItemParams.newBuilder();

        String uuid = jsonObject.getString("uuid");
        logger.debug("uuid:" + uuid);
        String secret = jsonObject.getString("secret");
        logger.debug("secret:" + secret);
        Integer type = jsonObject.getInteger("type");
        logger.debug("type:" + type);

        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)) {
            throw new MissingParamException();
        }
        if (type == null) {
            type = ItemRequest.RequestType.LIST_VALUE;
        }
        JSONObject paramObject = jsonObject.getJSONObject("item_params");
        if (paramObject == null && type != ItemRequest.RequestType.LIST_VALUE) {
            throw new MissingParamException();
        } else if (type != ItemRequest.RequestType.LIST_VALUE) {
            Integer item_id = paramObject.getInteger("id");
            String item_name = paramObject.getString("name");
            Integer item_stock = paramObject.getInteger("stock");
            paramBuilder
                    .setItemId(item_id)
                    .setItemName(item_name)
                    .setItemStock(item_stock);
        }
        builder.setUuid(uuid).setTypeValue(type).setSecret(secret).setParam(paramBuilder.build());
        return builder.build();
    }

    private ItemResponse getResponse(ItemRequest request)
            throws NotLoginException, SecretWrongException {
        ItemResponse.Builder builder = ItemResponse.newBuilder();
        String uuid = request.getUuid();
        logger.debug("uuid:" + uuid);
        String secret = request.getSecret();
        logger.debug("secret:" + secret);
        ItemParams param = request.getParam();
        ClientInstance ci = ManagerMain.clientInstanceMap.get(uuid);
        if (ci == null) {
            throw new NotLoginException();
        }
        if (!ci.getSecret().equals(secret)) {
            throw new SecretWrongException();
        }
        logger.debug("type:" + request.getType());
        switch (request.getType()) {
            case ADD: {
                if (ci.getUserType() != Login.LoginRequest.UserType.ADMIN) {
                    break;
                }
                ConsumableBean bean = new ConsumableBean();
                bean.setName(param.getItemName());
                bean.setStock(param.getItemStock());
                if (dao.insert(bean)) {
                    builder.setResult(ItemResponse.Result.SUCCESS);
                } else {
                    builder.setResult(ItemResponse.Result.FAILED);
                }
                break;
            }
            case EDIT: {
                if (ci.getUserType() != Login.LoginRequest.UserType.ADMIN) {
                    // 仅管理员
                    break;
                }
                ConsumableBean bean = new ConsumableBean();
                bean.setId(param.getItemId()); //item ID
                bean.setName(param.getItemName()); //item new Name
                bean.setStock(param.getItemStock()); //item new Stock
                if (dao.edit(bean)) {
                    builder.setResult(ItemResponse.Result.SUCCESS);
                } else {
                    builder.setResult(ItemResponse.Result.FAILED);
                }
                break;
            }
            case LIST: {
                ItemParams.Builder b = ItemParams.newBuilder();
                for (ConsumableBean bean : dao.listAll()) {
                    b
                            .setItemId(bean.getId())
                            .setItemName(bean.getName())
                            .setItemStock(bean.getStock())
                            .setItemAddedTime(ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getAddedTime()))
                            .setItemModifiedTime(ProtobufUtils.NativeTimestampToProtoTimestamp(bean.getModifiedTime()));
                    builder.addItemList(b.build());
                }
                break;
            }
            default: {
                break;
            }
        }
        return builder.build();
    }

    private void Response(HttpExchange t, ItemResponse resp, HandlerUtils.ContentType contentType)
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

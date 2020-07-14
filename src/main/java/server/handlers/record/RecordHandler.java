package server.handlers.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import consts.HttpStatusCode;
import dao.RecordDAO;
import exceptions.handler.ContentTypeException;
import exceptions.handler.LengthRequiredException;
import exceptions.handler.MissingParamException;
import model.protobuf.RecordProto.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CodingUtils;
import utils.HandlerUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RecordHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final RecordDAO dao = new RecordDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        byte[] req,resp;
        RecordRequest request;
        RecordResponse response;
        HandlerUtils.ContentType contentType = HandlerUtils.getContentType(httpExchange);

        try{
            int length = HandlerUtils.getContentLength(httpExchange);
            req = CodingUtils.streamToByteArray(httpExchange.getRequestBody(), length);
            if(contentType == HandlerUtils.ContentType.JSON) {
                request = jsonToProto(req);
            } else if(contentType == HandlerUtils.ContentType.PROTOBUF) {
                request = RecordRequest.parseFrom(req);
            } else {
                throw new ContentTypeException();
            }
            response = getResponse(request);
            Response(httpExchange, response, contentType);
        } catch (LengthRequiredException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.LENGTH_REQUIRED,0);
            logger.debug("Length Missing");
        } catch (ContentTypeException e){
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("Content Type");
        } catch (MissingParamException e) {
            httpExchange.sendResponseHeaders(HttpStatusCode.BAD_REQUEST, 0);
            logger.debug("参数错误");
        } finally {
            httpExchange.close();
        }
    }

    private RecordRequest jsonToProto(byte[] req)
            throws MissingParamException {
        String str = new String(req, StandardCharsets.UTF_8);
        RecordRequest.Builder builder = RecordRequest.newBuilder();
        logger.debug(str);
        JSONObject jsonObject = JSON.parseObject(str);
        String uuid = jsonObject.getString("uuid");
        String secret = jsonObject.getString("secret");

        if(StringUtils.isBlank(uuid) || StringUtils.isBlank(secret)){
            throw new MissingParamException();
        }
        builder
                .setUuid(uuid)
                .setSecret(secret);
        return builder.build();
    }

    private byte[] protoToJson(RecordResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", response.getResult());
        jsonObject.put("result_code", response.getResultValue());
        JSONArray array = new JSONArray();
        for(RecordInfo info: response.getRecordListList()){
            JSONObject tmp = new JSONObject();
            tmp.put("record_id", info.getRecordId());
            tmp.put("user_name", info.getUserName());
            tmp.put("item_id", info.getItemId());
            tmp.put("item_name", info.getItemName());
            tmp.put("item_count", info.getCount());
            array.add(tmp);
        }
        jsonObject.put("records", array);
        return jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    private RecordResponse getResponse(RecordRequest request) {
        RecordResponse.Builder builder = RecordResponse.newBuilder();
        RecordInfo.Builder ri_builder = RecordInfo.newBuilder();
        for(RecordInfo info : dao.listUnconfirmed() ){
            builder.addRecordList(info);
        }
        builder.setResult(RecordResponse.Result.SUCCESS);
        return builder.build();
    }

    private void Response(HttpExchange t, RecordResponse resp, HandlerUtils.ContentType type) throws IOException{
        byte[] data;
        if(type == HandlerUtils.ContentType.JSON){
            data = protoToJson(resp);
        } else {
            data = resp.toByteArray();
        }
        t.sendResponseHeaders(HttpStatusCode.HTTP_OK, data.length);
        t.getResponseBody().write(data);
    }
}

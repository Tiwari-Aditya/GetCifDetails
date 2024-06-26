package in.co.indusnet.java21poc.utility;

import in.co.indusnet.java21poc.response.ResponseData;

import java.util.Map;

public class ResponseHelper {
    public static ResponseData responseSender(String statusMessage , int statusCode) {
        ResponseData response = new ResponseData();
        response.setStatusCode(statusCode);
        response.setStatusMessage(statusMessage);
        return response;
    }

    public static ResponseData responseSenderData(String statusMessage , int statusCode, Map<String, String> responseData) {
        ResponseData response = new ResponseData();
        response.setStatusCode(statusCode);
        response.setStatusMessage(statusMessage);
        response.responseData.putAll(responseData);
        return response;
    }
}

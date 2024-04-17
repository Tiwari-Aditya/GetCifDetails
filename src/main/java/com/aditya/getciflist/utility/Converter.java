package com.aditya.getciflist.utility;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class Converter {
    public static String xmlToJson(String xmlData) {
        JSONObject jObject = XML.toJSONObject(xmlData);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Object json = null;
        try {
            json = mapper.readValue(jObject.toString(), Object.class);
        } catch (JsonParseException e) {

        } catch (JsonMappingException e) {

        } catch (IOException e) {

        }
        String output = null;
        try {
            output = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {

        }
        return output;

    }
}

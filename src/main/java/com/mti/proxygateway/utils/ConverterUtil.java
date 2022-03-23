package com.mti.proxygateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ConverterUtil {

    public static Map<String, Object> convertToJson(String jsonString) {

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> map = null;

        try {
            map = om.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return map;
    }

}

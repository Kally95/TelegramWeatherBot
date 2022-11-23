package com.kallyio.TelegramWeatherBot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kallyio.TelegramWeatherBot.entities.GeoCoordinates;
import com.kallyio.TelegramWeatherBot.entities.WeatherReport;

import java.io.IOException;

public class JsonMapper {

    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();
    }

    public static <T> T jsonToMapLocation(String json, Class<T> c) throws JsonProcessingException {
        try {
            Object obj = objectMapper.readValue(json, c);
            return c.cast(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

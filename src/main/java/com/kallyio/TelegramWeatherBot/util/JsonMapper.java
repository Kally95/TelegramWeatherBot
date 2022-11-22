package com.kallyio.TelegramWeatherBot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kallyio.TelegramWeatherBot.entities.WeatherReport;

import java.io.IOException;

public class JsonMapper {
    public static WeatherReport jsonToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WeatherReport map = mapper.readValue(json, WeatherReport.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

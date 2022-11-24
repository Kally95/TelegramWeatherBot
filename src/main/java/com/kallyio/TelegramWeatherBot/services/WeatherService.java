package com.kallyio.TelegramWeatherBot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherReport;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.http.WeatherClient;
import com.kallyio.TelegramWeatherBot.util.JsonMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherService {
    @Autowired
    private WeatherConfig weatherConfig;
    @Autowired
    private WeatherClient weatherClient;

    public WeatherResponse getWeather(Location latLng) {
        return weatherClient.weatherAPIConsumer(latLng);
    }

    public String generateWeatherReport(WeatherResponse resp, String sendersName) {
        if(resp.getStatus() == HttpStatus.SC_OK){
            WeatherReport weatherReport;
            try {
                weatherReport = JsonMapper.jsonToPojoMapper(resp.getBody(), WeatherReport.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String weatherDesc = weatherReport.getWeather().get(0).getDescription();
            String weatherLoc = weatherReport.getName();
            double temp = weatherReport.getTemp().getTemp();
            double tempFeels = weatherReport.getTemp().getFeels_like();
            return String.format("Hi %s, in %s the weather is currently %.2f degrees with %s and feels like %.2f degrees from a humans perspective",
                    sendersName, weatherLoc, temp, weatherDesc, tempFeels);
            }
        return "Error occurred whilst fetching the weather. If this persists, please contact Kally-95@hotmail.com";
    }
}

package com.kallyio.TelegramWeatherBot.services;

import com.kallyio.TelegramWeatherBot.entities.WeatherReport;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.util.JsonMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WeatherService {
    @Autowired
    private WeatherConfig weatherConfig;
    private static final String URI_RESOURCE = "https://api.openweathermap.org/data/2.5/weather?";

    public WeatherResponse getWeather() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URI_RESOURCE
                                + "lat="+weatherConfig.getLat()
                                +"&lon="+weatherConfig.getLon()
                                +"&appid="+weatherConfig.getAPIKey()
                                +"&units=metric"
                        )).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.SC_OK) {
                return new WeatherResponse(response.body(),HttpStatus.SC_OK);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new WeatherResponse("No response found", HttpStatus.SC_OK);
    }

    public String generateWeatherReport(WeatherResponse resp, String sendersName) {
        if(resp.getStatus() == HttpStatus.SC_OK){
            WeatherReport entity = JsonMapper.jsonToMap(resp.getBody());
                String weatherDesc = entity.getWeather().get(0).description;
                String weatherLoc = entity.getName();
                double temp = entity.getTemp().temp;
                double tempFeels = entity.getTemp().feels_like;
                return String.format("Hi %s, in %s the weather is currently %.2f degrees with %s and feels like %.2f degrees from a humans perspective",
                        sendersName, weatherLoc, temp, weatherDesc, tempFeels);
            }
        return "Error occurred while fetching the weather. If this persists, please contact Kally-95@hotmail.com";
    }
}

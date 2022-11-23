package com.kallyio.TelegramWeatherBot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kallyio.TelegramWeatherBot.entities.Location;
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
    //Field injection bad.
    @Autowired
    private WeatherConfig weatherConfig;
    //why not move this uri somewhere else? Renamed whetherConfig? properties file?
    private static final String URI_RESOURCE = "https://api.openweathermap.org/data/2.5/weather?";

    //latLng is terrible name :) location, coordinates, ...
    public WeatherResponse getWeather(Location latLng) {
        //TODO - Decouple API call functionality, move to another class/package.

        //You have spring in the project you should probably switch to restTemplate
        //https://www.baeldung.com/rest-template
        //Even if you don't switch to restTemplate, I guess this is what you meant in to do,
        //yes you should wrap request sending logic in some method in different class.
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URI_RESOURCE
                                + "lat="+latLng.lat
                                +"&lon="+latLng.lng
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
            WeatherReport entity;
            try {
                entity = JsonMapper.jsonToMapLocation(resp.getBody(), WeatherReport.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String weatherDesc = entity.getWeather().get(0).description;
            String weatherLoc = entity.getName();
            double temp = entity.getTemp().temp;
            double tempFeels = entity.getTemp().feels_like;
            return String.format("Hi %s, in %s the weather is currently %.2f degrees with %s and feels like %.2f degrees from a humans perspective",
                    sendersName, weatherLoc, temp, weatherDesc, tempFeels);
            }
        return "Error occurred whilst fetching the weather. If this persists, please contact Kally-95@hotmail.com";
    }
}

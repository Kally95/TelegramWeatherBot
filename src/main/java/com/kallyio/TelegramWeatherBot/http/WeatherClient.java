package com.kallyio.TelegramWeatherBot.http;

import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.services.WeatherConfig;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
@AllArgsConstructor
public class WeatherClient {
    private WeatherConfig weatherConfig;
    private static final String URI_RESOURCE = "https://api.openweathermap.org/data/2.5/weather?";
    public WeatherResponse weatherAPIConsumer(Location latLng) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URI_RESOURCE
                +"lat="+latLng.getLat()
                +"&lon="+latLng.getLng()
                +"&appid="+weatherConfig.getAPIKey()
                +"&units=metric", String.class);

        if (response.getStatusCodeValue() == HttpStatus.SC_OK) {
            return new WeatherResponse(response.getBody(), HttpStatus.SC_OK);
        }
        return new WeatherResponse("No response found", HttpStatus.SC_OK);
    }

}

package com.kallyio.TelegramWeatherBot.http;

import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.config.StoredKeys;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static com.kallyio.TelegramWeatherBot.util.Constants.OPEN_WEATHER_MAP_URL;

@Component
@AllArgsConstructor
public class WeatherClient {
    private StoredKeys StoredKeys;
    public WeatherResponse weatherAPIConsumer(Location coordinates) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(OPEN_WEATHER_MAP_URL
                +"lat="+coordinates.getLatitude()
                +"&lon="+coordinates.getLongitude()
                +"&appid="+StoredKeys.getWeatherApiKey()
                +"&units=metric", String.class);

        if (response.getStatusCodeValue() == HttpStatus.SC_OK) {
            return new WeatherResponse(response.getBody(), HttpStatus.SC_OK);
        }
        return new WeatherResponse("No response found", HttpStatus.SC_OK);
    }

}

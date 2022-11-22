package com.kallyio.TelegramWeatherBot.services;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherConfig {
    private final String APIKey = System.getenv("API_KEY");
    private final String lat = "51.444641";
    private final String lon = "-0.020106";
}

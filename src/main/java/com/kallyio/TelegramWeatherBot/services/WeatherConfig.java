package com.kallyio.TelegramWeatherBot.services;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherConfig {
    private final String APIKey = System.getenv("API_KEY");
}

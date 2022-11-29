package com.kallyio.TelegramWeatherBot.http;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WeatherClientBuilder {

    @Bean
    public WebClient.Builder getWeatherClientBuilder() {
        return WebClient.builder();
    }

}

package com.kallyio.TelegramWeatherBot.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponse {
    private String body;
    private int status;
}

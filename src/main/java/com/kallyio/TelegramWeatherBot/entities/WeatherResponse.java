package com.kallyio.TelegramWeatherBot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherResponse {
    private String body;
    private int status;
}

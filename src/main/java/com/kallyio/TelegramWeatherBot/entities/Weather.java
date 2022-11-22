package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather{
    public String description;
}
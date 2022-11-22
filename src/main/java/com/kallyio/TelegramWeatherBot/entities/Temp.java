package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temp {
    public double temp;
    public double feels_like;

}
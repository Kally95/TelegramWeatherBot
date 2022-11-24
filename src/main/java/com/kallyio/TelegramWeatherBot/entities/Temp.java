package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
//Is it temperature or some temporary values that you store here?
public class Temp {
    private double temp;
    private double feels_like;
}
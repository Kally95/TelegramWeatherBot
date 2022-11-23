package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
//Is it temperature or some temporary values that you store here?
public class Temp {
    public double temp;
    //snake_case :,)
    public double feels_like;

}
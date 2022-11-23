package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
//Terrible class name
public class Sys{
    public String country;
}

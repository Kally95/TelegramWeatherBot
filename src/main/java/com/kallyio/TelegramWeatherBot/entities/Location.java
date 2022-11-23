package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    //abbreviations are bad. Also you are it's inconsistent with com.kallyio.TelegramWeatherBot.services.WeatherConfig.lon
    public double lat;
    public double lng;
}

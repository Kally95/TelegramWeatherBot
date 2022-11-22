package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReport {
    public ArrayList<Weather> weather;
    @JsonProperty("main")
    public Temp temp;
    public Sys sys;
    public int id;
    public String name;
}

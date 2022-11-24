package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReport {
    private ArrayList<Weather> weather;
    @JsonProperty("main")
    private Temp temp;
    private Sys sys;
    private int id;
    private String name;
}

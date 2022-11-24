package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lng")
    private double longitude;

    public Location(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
}

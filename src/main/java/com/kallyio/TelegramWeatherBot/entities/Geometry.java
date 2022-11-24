package com.kallyio.TelegramWeatherBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
//I'm not sure what is reason for encapsulating Location inside Geometry (not saying it's bad, just confusing to me, but maybe I missed something)
public class Geometry {
    private Location location;
}

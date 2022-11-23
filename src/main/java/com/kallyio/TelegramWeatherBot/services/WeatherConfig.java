package com.kallyio.TelegramWeatherBot.services;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
//this is small project so I would let it pass, but this should be in some config package
//Also class name is not describing what this is. You only use the APIKey from this class, so it's more of the Key Config/Store?
public class WeatherConfig {
    private final String APIKey = System.getenv("API_KEY");
    //those 2 fields are never used, also in java we rather use full names for variables. Fields especially. whetherConfig.lon <- this could be anything.
    private final String lat = "51.444641";
    private final String lon = "-0.020106";
}

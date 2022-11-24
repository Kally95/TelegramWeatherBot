package com.kallyio.TelegramWeatherBot.services;

import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.http.GeocoderClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GeocoderService {
    private GeocoderClient geocoderClient;

    public Location getLocationCoordinates(String address) {
        {
            Location coords = geocoderClient.getLocation(address);
            if (coords != null) return coords;
        }
        return null;
    }
}


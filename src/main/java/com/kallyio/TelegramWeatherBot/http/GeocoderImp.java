package com.kallyio.TelegramWeatherBot.http;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.kallyio.TelegramWeatherBot.config.StoredKeys;
import com.kallyio.TelegramWeatherBot.entities.GeoCoordinates;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.util.JsonMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GeocoderImp {
    private StoredKeys storedKeys;

    public Location getLocationCoordinates(String address) {
        {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(storedKeys.getGoogleApiKey())
                    .build();
            try {
                GeocodingResult[] results = GeocodingApi.geocode(context,
                        address).await();
                if(results.length > 0) {
                    String json = JsonMapper.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(results[0]);
                    GeoCoordinates coords = JsonMapper.jsonToPojoMapper(json, GeoCoordinates.class);
                    return new Location(coords.getGeometry().getLocation());
                }
            } catch (Exception e) {
               e.printStackTrace();
            } finally {
                context.shutdown();
            }
        }
        return null;
    }
}


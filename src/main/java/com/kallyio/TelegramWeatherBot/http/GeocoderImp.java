package com.kallyio.TelegramWeatherBot.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.kallyio.TelegramWeatherBot.entities.GeoCoordinates;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.util.JsonMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GeocoderImp {
    public static Location getLocationCoordinates(String address) throws IOException, InterruptedException, ApiException {
        //TODO - Decouple API call functionality, move to another class/package.
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(System.getenv("GOOGLE_API_KEY"))
                .build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    address).await();

            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results[0]);
            GeoCoordinates coords = JsonMapper.jsonToMapLocation(json, GeoCoordinates.class);

            Location location = new Location();
            location.lat = coords.geometry.location.lat;
            location.lng = coords.geometry.location.lng;

            return location;
        } catch (IOException e) {
            throw e;
        } catch (InterruptedException i){
            throw i;
        } catch (ApiException a) {
            throw a;
        } finally {
            context.shutdown();
        }
    }

}


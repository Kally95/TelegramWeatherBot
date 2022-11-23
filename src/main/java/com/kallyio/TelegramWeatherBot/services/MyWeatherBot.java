package com.kallyio.TelegramWeatherBot.services;

import com.google.maps.errors.ApiException;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.http.GeocoderImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class MyWeatherBot extends TelegramLongPollingBot {

   @Autowired
    private WeatherService weatherService;

    @Override
    public String getBotUsername() {
        return "officialweathrbot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TG_BOT_KEY");
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText().toLowerCase();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);

            String sendersName = update.getMessage().getFrom().getUserName();

            String[] values = message_text.split(" ");
            String keyword = values[0].trim();
            String locationWord = values[1].trim();

            Location latLng;

            if(
                    keyword.equals("weather") &&
                    locationWord.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")
            ){
                try {
                    latLng = GeocoderImp.getLocationCoordinates(locationWord);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }

                WeatherResponse response = weatherService.getWeather(latLng);

                message.setText(weatherService.generateWeatherReport(response, sendersName));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                message.setText("Sorry, I didn't quite get that. Please use the following format 'weather <city>'." +
                        " As an example, type 'weather London'");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

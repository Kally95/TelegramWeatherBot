package com.kallyio.TelegramWeatherBot.services;

import com.google.maps.errors.ApiException;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import com.kallyio.TelegramWeatherBot.http.GeocoderImp;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class MyWeatherBot extends TelegramLongPollingBot {
    //Field injection bad.
    @Autowired
    private WeatherService weatherService;

    @Override
    public String getBotUsername() {
        //should be in config/properties
        return "officialweathrbot";
    }

    @Override
    public String getBotToken() {
        //should be in config/properties
        return System.getenv("TG_BOT_KEY");
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            //snake_case? just why? :,)
            String message_text = update.getMessage().getText().toLowerCase();
            //getChatId() returns wrapper Long so it can probably return null in that case your primitive long will work just fine and just have 0
            //this can lead to getting(or sending) message to/from wrong chat and could be hard to find the cause
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);

            //I don't know this api so I don't know how possible it is that message/from is null, but in production code
            //You should make null checks after each dot update.getMessage().getFrom().getUserName()
            //It would probably be overhead in here, just wanted to make you aware
            String sendersName = update.getMessage().getFrom().getUserName();

            String[] values = message_text.split(" ");

            String keyword = null;
            String locationWord = null;

            if(values.length < 2) {
                 message.setText("Sorry, I didn't quite get that. Please use the following format 'weather <city>'." +
                        " As an example, type 'weather London'");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    //what you did is not bad but
                    //you are using Lombok so you can use @SneakyThrows, which has advantage of not hiding the exception type
                    //and you will not need this try/catch at all then
                    throw new RuntimeException(e);
                }
            } else {
                keyword = values[0].trim();
                locationWord = values[1].trim();

                //latLng is terrible name :) location, coordinates, ...
                Location latLng;

                if (values.length == 2 &&
                        //those null checks are made too late, you would already got NPE at trim() above if any of those would be null.
                        //also there is a trick to checking this without worring about null. You can invert the equals:
                        //"weather".equals(keyword) <- "weather" is litteral so it can't be null, so no need to worry about npe and if the keyword is null it will just return false
                        locationWord != null &&
                        keyword != null &&
                        keyword.equals("weather") &&
                        //https://www.baeldung.com/java-regex-pre-compile
                        locationWord.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")
                ) {
                    try {
                        //if you decide to use sneakyThrows, try/catch can be removed
                        latLng = GeocoderImp.getLocationCoordinates(locationWord);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }

                    //do you need both methods?
                    // If you call a class method just to use respose as input to another method of the same class,
                    // then maybe you can make getWeather private and just pass one more parameter to generateWeatherReport,
                    // and call getWeather in there
                    //if you have another use for getWeather this could be fine tho
                    WeatherResponse response = weatherService.getWeather(latLng);

                    message.setText(weatherService.generateWeatherReport(response, sendersName));

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    //it would probably be possible to do all the validation together.
                    // But even if you keep it at 2 places, you should keep this message in variable and reuse.
                    // If you will want to change it in future you won't have to search for each usage.
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

}

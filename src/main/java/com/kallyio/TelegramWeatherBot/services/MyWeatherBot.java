package com.kallyio.TelegramWeatherBot.services;

import com.kallyio.TelegramWeatherBot.config.StoredKeys;
import com.kallyio.TelegramWeatherBot.util.Constants;
import com.kallyio.TelegramWeatherBot.entities.Location;
import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Component
public class MyWeatherBot extends TelegramLongPollingBot {
    private WeatherService weatherService;
    private GeocoderService geocoderService;
    private StoredKeys storedKeys;

    @Override
    public String getBotUsername() {
        return Constants.TELEGRAM_BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return storedKeys.getTelegramBotKey();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] values = update.getMessage().getText().toLowerCase().split(" ");
            if(values.length == Constants.EXPECTED_ARRAY_SIZE) {
                if (values[0].trim().equals(Constants.WEATHER) && values[1].trim().matches(Constants.WEATHER_NAME_REGEX)) {
                    Location coordinates = geocoderService.getLocationCoordinates(values[1].trim());
                    if(coordinates != null) {
                        WeatherResponse response = weatherService.getWeather(coordinates);
                        message.setText(weatherService.generateWeatherReport(response, update.getMessage().getFrom().getUserName()));
                        execute(message);
                        return;
                    }
                }
            }
        }
        message.setText(Constants.BOT_ERROR_MESSAGE);
        execute(message);
    }
}

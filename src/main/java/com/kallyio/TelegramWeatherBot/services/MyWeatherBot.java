package com.kallyio.TelegramWeatherBot.services;

import com.kallyio.TelegramWeatherBot.entities.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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
            String sendersName = update.getMessage().getFrom().getUserName();
            // TODO If the message contains the keyword weather and a valid city
            if(message_text.trim().contains("weather")) {

                SendMessage message = new SendMessage();

                message.setChatId(chat_id);

                WeatherResponse response = weatherService.getWeather();

                message.setText(weatherService.generateWeatherReport(response, sendersName));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

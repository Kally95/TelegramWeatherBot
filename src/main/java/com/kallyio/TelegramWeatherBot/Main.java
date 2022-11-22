package com.kallyio.TelegramWeatherBot;

import com.kallyio.TelegramWeatherBot.services.MyWeatherBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import javax.annotation.PostConstruct;

@SpringBootApplication
@AutoConfiguration
public class Main {

	@Autowired
	MyWeatherBot myWeatherBot;

	public static void main(String[] args){
		SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void startUp() {
		TelegramBotsApi telegramBotsApi;
		try {
			telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}


		try {
			telegramBotsApi.registerBot(myWeatherBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}

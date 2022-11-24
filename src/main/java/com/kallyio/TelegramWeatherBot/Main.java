package com.kallyio.TelegramWeatherBot;

//clean unused imports. If you use IntelliJ ctrl + alt + K will clean your code
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

	//avoid field injection if it's not necessary
	//https://eng.zemosolabs.com/when-not-to-autowire-in-spring-spring-boot-93e6a01cb793
	@Autowired
	MyWeatherBot myWeatherBot;

	public static void main(String[] args){
		SpringApplication.run(Main.class, args);
	}

	//this doesn't fit here and will probably break if you use constructor injection instead of Autowired
	//you should move this to some new class
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

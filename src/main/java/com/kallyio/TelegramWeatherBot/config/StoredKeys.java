package com.kallyio.TelegramWeatherBot.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
@Getter
@Component
public final class StoredKeys {
    private final String weatherApiKey = System.getenv("API_KEY");
    private final String googleApiKey = System.getenv("GOOGLE_API_KEY");
    private String telegramBotKey = System.getenv("TG_BOT_KEY");

}

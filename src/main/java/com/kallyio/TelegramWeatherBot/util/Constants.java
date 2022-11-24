package com.kallyio.TelegramWeatherBot.util;


public class Constants {
    public static final int EXPECTED_ARRAY_SIZE = 2;
    public static final String BOT_ERROR_MESSAGE = "Sorry, I didn't quite get that. Please use the following format 'weather <city>'. As an example, type 'weather London'";
    public final static String WEATHER_NAME_REGEX = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";

    public static final String OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org/data/2.5/weather?";
    public final static String TELEGRAM_BOT_NAME = "officialweathrbot";
    public static final String WEATHER = "weather";
}

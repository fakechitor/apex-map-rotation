package org.fakechitor.apexmaprotation.config;

import org.fakechitor.apexmaprotation.bot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotInitializer {

    private static final Logger log = LoggerFactory.getLogger(BotInitializer.class);

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(telegramBot);
            log.info("Telegram бот успешно зарегистрирован и запущен!");
        } catch (TelegramApiException e) {
            log.error("Ошибка регистрации бота: ", e);
            throw e;
        }
        return botsApi;
    }
}
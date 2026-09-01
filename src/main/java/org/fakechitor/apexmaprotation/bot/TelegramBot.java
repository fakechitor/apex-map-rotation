package org.fakechitor.apexmaprotation.bot;

import org.fakechitor.apexmaprotation.service.ApexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private final String botUsername;

    private final ApexService apexService;

    public TelegramBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            ApexService apexService
            ) {
        super(botToken);
        this.botUsername = botUsername;
        this.apexService = apexService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if ("/start".equalsIgnoreCase(messageText)) {
                sendMessage(chatId, "👋 Привет! Я бот ротации карт Apex.");
            } else if ("/ranked".equalsIgnoreCase(messageText)) {
                sendMessage(chatId, apexService.getRankedInfo().toString());
            } else if ("/maps".equalsIgnoreCase(messageText)) {
                sendMessage(chatId, apexService.getFormattedRankedInfo());
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .parseMode("HTML")
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в чат {}", chatId, e);
        }
    }
}
package org.fakechitor.apexmaprotation.bot;

import org.fakechitor.apexmaprotation.model.TelegramUser;
import org.fakechitor.apexmaprotation.service.ApexService;
import org.fakechitor.apexmaprotation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final String WELCOME_MESSAGE_TEXT = "Это бот для просмотра ротации карт";


    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    public static final String MAP_ROTATION = "Ротация карт";
    public static final String FAVOURITE_MAPS = "Избранные карты";


    private final String botUsername;

    private final ApexService apexService;

    private final UserService userService;

    public TelegramBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            ApexService apexService,
            UserService userService
    ) {
        super(botToken);
        this.botUsername = botUsername;
        this.apexService = apexService;
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String messageText = message.getText();
            Long chatId = message.getChatId();
            User telegramUser = message.getFrom();

            if ("/start".equalsIgnoreCase(messageText)) {
                registerUserIfNotFound(telegramUser);
                sendMainMenu(chatId);
            } else if (MAP_ROTATION.equalsIgnoreCase(messageText)) {
                sendMessage(chatId, apexService.getFormattedTelegramRankedInfo());
            } else if ("/ranked".equalsIgnoreCase(messageText)) {
                sendMessage(chatId, apexService.getRankedInfo().toString());
            } else if ("/maps".equalsIgnoreCase(messageText)) {
                sendMessage(chatId, apexService.getFormattedTelegramAllInfo());
            }
        }
    }

    private void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    private void sendMessage(Long chatId, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .parseMode("HTML")
                .build();

        if (replyKeyboardMarkup != null) {
            message.setReplyMarkup(replyKeyboardMarkup);
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в чат {}", chatId, e);
        }
    }

    private void sendMainMenu(Long chatId) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(MAP_ROTATION);
        row1.add(FAVOURITE_MAPS);

        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);

        sendMessage(chatId, WELCOME_MESSAGE_TEXT, keyboardMarkup);
    }

    private void registerUserIfNotFound(User user) {
        var registeredUser = userService.findByTelegramId(user.getId());

        if (registeredUser.isEmpty()) {
            TelegramUser telegramUser = TelegramUser.builder()
                    .username(user.getUserName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .telegramId(user.getId())
                    .build();

            userService.save(telegramUser);

            log.info("User with username {} has registered", user.getUserName());
        }
    }
}
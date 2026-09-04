package org.fakechitor.apexmaprotation.service;

import lombok.RequiredArgsConstructor;
import org.fakechitor.apexmaprotation.model.TelegramUser;
import org.fakechitor.apexmaprotation.repository.TelegramUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TelegramUserRepository telegramUserRepository;

    public Optional<TelegramUser> findByTelegramId(Long telegramId) {
        var  telegramUser = telegramUserRepository.findByTelegramId(telegramId);
        return Optional.ofNullable(telegramUser);
    }

    public TelegramUser save(TelegramUser user) {
        return telegramUserRepository.save(user);
    }
}

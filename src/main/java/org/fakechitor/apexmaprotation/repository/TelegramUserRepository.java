package org.fakechitor.apexmaprotation.repository;

import org.fakechitor.apexmaprotation.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    TelegramUser findByTelegramId(Long telegramId);
}

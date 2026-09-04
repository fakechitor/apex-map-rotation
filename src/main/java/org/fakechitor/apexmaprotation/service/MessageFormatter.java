package org.fakechitor.apexmaprotation.service;

import org.fakechitor.apexmaprotation.dto.BaseMapInfo;
import org.fakechitor.apexmaprotation.dto.MapRotationResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MessageFormatter {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    public String formatRotationMessage(MapRotationResponse response) {
        if (response == null) {
            return "⚠️ Не удалось получить данные о ротации карт.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("🎮 <b>Ротация карт Apex Legends</b>\n\n");

        // Ранговый режим
        if (response.ranked() != null) {
            sb.append("🏆 <b>Рейтинговый режим (Ranked):</b>\n");
            appendModeInfo(sb, response.ranked().current(), response.ranked().next());
            sb.append("\n");
        }

        // Обычный Battle Royale (паблик)
        if (response.battleRoyale() != null) {
            sb.append("🎯 <b>Battle Royale (Паблик):</b>\n");
            appendModeInfo(sb, response.battleRoyale().current(), response.battleRoyale().next());
            sb.append("\n");
        }

        // Mixtape (Контроль / КБМ / Оружейка)
        if (response.mixtape() != null) {
            sb.append("🎪 <b>Mixtape:</b>\n");
            appendModeInfo(sb, response.mixtape().current(), response.mixtape().next());
        }

        return sb.toString();
    }

    private void appendModeInfo(StringBuilder sb, BaseMapInfo current, BaseMapInfo next) {

        sb.append("📍 <b>Сейчас:</b> ").append(current.mapName()).append("\n");
        if (current.remainingTime() != null) {
            sb.append("⏳ <b>Осталось:</b> ").append(current.remainingTime()).append("\n");
        }
        if (current.dateEnd() != null) {
            sb.append("🏁 <b>Смена в:</b> ").append(current.withMoscowTime().dateEnd().format(TIME_FMT)).append(" (МСК)\n");
        }
        sb.append("➡️ <b>Следующая:</b> ").append(next.mapName()).append("\n");
    }

    public String formatRankedRotationMessage(MapRotationResponse response) {
        if (response == null) {

        }
        return "";
    }

}

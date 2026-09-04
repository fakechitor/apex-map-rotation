package org.fakechitor.apexmaprotation.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fakechitor.apexmaprotation.client.ApexDataClient;
import org.fakechitor.apexmaprotation.dto.BaseMapInfo;
import org.fakechitor.apexmaprotation.dto.MapRotationResponse;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApexService {

    private final ObjectMapper objectMapper;

    private final ApexDataClient apexDataClient;

    private final MessageFormatter messageFormatter;

    public Map<String, BaseMapInfo> getRankedInfo() {
        MapRotationResponse response = apexDataClient.parseMapData();

        BaseMapInfo current = response.ranked().current();
        BaseMapInfo next = response.ranked().next();

        return Map.of(
                "current", current.withMoscowTime(),
                "next", next.withMoscowTime()
        );
    }

    public String getFormattedTelegramAllInfo() {
        MapRotationResponse response = apexDataClient.parseMapData();
        return messageFormatter.formatRotationMessage(response);
    }
    public String getFormattedTelegramRankedInfo() {
        MapRotationResponse response = apexDataClient.parseMapData();
        return messageFormatter.formatRankedRotationMessage(response);
    }

}

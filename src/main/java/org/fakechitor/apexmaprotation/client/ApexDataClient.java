package org.fakechitor.apexmaprotation.client;

import lombok.RequiredArgsConstructor;
import org.fakechitor.apexmaprotation.dto.MapRotationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class ApexDataClient {

    private final static Long VERSION_NUM = 2L;

    private final RestClient apexRestClient;

    @Value("${apex.api.key}")
    private String apiKey;

    public MapRotationResponse parseMapData() {
        return apexRestClient.get()
                .uri("/maprotation?version={version}&auth={auth}", VERSION_NUM , apiKey)
                .retrieve()
                .body(MapRotationResponse.class);
    }

}

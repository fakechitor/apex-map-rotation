package org.fakechitor.apexmaprotation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "apex.api")
public record ApexApiProperties(
        @DefaultValue("")
        String baseUrl,
        @DefaultValue("10")
        int timeoutSeconds
) {
}

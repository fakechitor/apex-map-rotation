package org.fakechitor.apexmaprotation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MapRotationResponse(
        @JsonProperty("battle_royale")
        MapRotationMode battleRoyale,

        @JsonProperty("ranked")
        MapRotationMode ranked,

        @JsonProperty("mixtape")
        MapRotationMode mixtape,

        @JsonProperty("ltm")
        MapRotationMode ltm
) {
}

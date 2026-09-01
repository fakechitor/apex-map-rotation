package org.fakechitor.apexmaprotation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MapRotationMode(
        @JsonProperty("current")
        BaseMapInfo current,

        @JsonProperty("next")
        BaseMapInfo next
) {
}

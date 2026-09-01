package org.fakechitor.apexmaprotation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BaseMapInfo(
        @JsonProperty("map")
        String mapName,

        @JsonProperty("readableDate_start")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateStart,

        @JsonProperty("readableDate_end")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateEnd,

        @JsonProperty("asset")
        String mapIcon,

        @JsonProperty("remainingTimer")
        LocalTime remainingTime
) {
        public BaseMapInfo withMoscowTime() {
                return new BaseMapInfo(
                        this.mapName,
                        this.dateStart != null ? this.dateStart.plusHours(3) : null,
                        this.dateEnd != null ? this.dateEnd.plusHours(3) : null,
                        this.mapIcon,
                        this.remainingTime
                );
        }

}

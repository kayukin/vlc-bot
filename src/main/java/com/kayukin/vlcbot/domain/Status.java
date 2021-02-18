package com.kayukin.vlcbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Status {
    private Boolean fullscreen;

    @JsonProperty("audiodelay")
    private Integer audioDelay;

    @JsonProperty("apiversion")
    private Integer apiVersion;

    @JsonProperty("currentplid")
    private Integer currentPlId;

    private Integer time;

    private Integer volume;

    private Integer length;

    private Boolean random;
    @JsonProperty("audiofilters")
    private AudioFilters audioFilters;

    private Integer rate;
    @JsonProperty("videoeffects")
    private VideoEffects videoEffects;

    private String state;

    private Boolean loop;

    private String version;

    private Integer position;

    private Boolean repeat;
    @JsonProperty("subtitledelay")
    private Integer subtitleDelay;

    private List<Object> equalizer;
}

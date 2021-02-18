package com.kayukin.vlcbot.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AudioFilters {
    @JsonProperty("filter_0")
    private String filter;
}



package com.kayukin.vlcbot.domain;

import lombok.Data;

@Data
public class VideoEffects {
    private Integer hue;
    private Integer saturation;
    private Integer contrast;
    private Integer brightness;
    private Integer gamma;
}

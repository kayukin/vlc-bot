package com.kayukin.vlcbot;

import com.kayukin.vlcbot.domain.Status;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Component
public class VlcClient {
    public static final String STATUS_JSON = "/status.json";
    private final RestTemplate restTemplate;

    public VlcClient(RestTemplateBuilder restTemplateBuilder, MappingJackson2HttpMessageConverter messageConverter) {
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        this.restTemplate = restTemplateBuilder
                .rootUri("http://localhost:8080/requests/")
                .basicAuthentication("", "1")
                .build();
    }

    public Status getStatus() {
        return restTemplate
                .getForObject(STATUS_JSON, Status.class);
    }

    //?command=in_play&input=<uri>
    public Status play(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(STATUS_JSON)
                .queryParam("command", "in_play")
                .queryParam("input", url);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, Status.class)
                .getBody();
    }

    public Status fullscreen() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(STATUS_JSON)
                .queryParam("command", "fullscreen");

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, Status.class)
                .getBody();
    }
}

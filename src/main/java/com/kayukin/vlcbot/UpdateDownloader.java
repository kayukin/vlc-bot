package com.kayukin.vlcbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.kayukin.vlcbot.ShutdownListener.ShutdownEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Profile("prod")
@Slf4j
public class UpdateDownloader {
    private final RestTemplate restTemplate;
    private final static String curRel = "";
    private final ApplicationEventPublisher applicationEventPublisher;

    public UpdateDownloader(RestTemplateBuilder restTemplateBuilder,
                            ApplicationEventPublisher applicationEventPublisher) {
        this.restTemplate = restTemplateBuilder
                .build();
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 3000)
    public void test() {
        checkUpdate();
    }

    @SneakyThrows
    public void checkUpdate() {
        log.info("Checking for updates");
        final var latestRelease = restTemplate.getForObject("https://api.github.com/repos/kayukin/vlc-bot/releases/latest", JsonNode.class);
        final var tagName = latestRelease.get("tag_name").asText();
        if (!tagName.equals(curRel)) {
            log.info("Update found, downloading");
            final var url = latestRelease.path("assets").path(0).path("browser_download_url").asText();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> response = restTemplate
                    .exchange(url, HttpMethod.GET, entity, byte[].class);
            Files.write(Paths.get("update.jar"), response.getBody());
            log.info("Downloaded");
            Runtime.getRuntime().exec("update.bat");
            applicationEventPublisher.publishEvent(new ShutdownEvent());
        }
    }
}

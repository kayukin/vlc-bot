package com.kayukin.vlcbot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URL;

@Component
@Slf4j
public class VlcBot extends TelegramLongPollingBot {
    private final VlcClient vlcClient;
    private final String botUsername;
    private final String botToken;

    public VlcBot(VlcClient vlcClient,
                  @Value("${bot.username}") String botUsername,
                  @Value("${bot.token}") String botToken) {
        this.vlcClient = vlcClient;
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        final var text = update.getMessage().getText();
        final var url = new URL(text);
        System.out.println(url.toString());
        try {
            vlcClient.play(url.toString());
            waitUntilPlayed();
            vlcClient.fullscreen();
        } catch (ResourceAccessException e) {
            Runtime.getRuntime().exec("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe " + url);
            waitUntilPlayed();
            vlcClient.fullscreen();
        }
    }

    @SneakyThrows
    private void waitUntilPlayed() {
        String state = null;
        while (!"playing".equals(state)) {
            Thread.sleep(500);
            state = vlcClient.getStatus().getState();
            log.info("Current state: {}", state);
        }
    }
}

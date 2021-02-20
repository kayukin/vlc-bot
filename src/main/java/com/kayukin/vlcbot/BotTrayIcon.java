package com.kayukin.vlcbot;

import com.kayukin.vlcbot.ShutdownListener.ShutdownEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

@Component
public class BotTrayIcon extends TrayIcon implements DisposableBean {

    private static final String IMAGE_PATH = "/spy-bot.png";
    private static final String TOOLTIP = "Vlc Bot";

    private final PopupMenu popup;
    private final SystemTray tray;
    private final ApplicationEventPublisher eventPublisher;

    public BotTrayIcon(ApplicationEventPublisher eventPublisher) {
        super(createImage(IMAGE_PATH, TOOLTIP), TOOLTIP);
        this.eventPublisher = eventPublisher;
        popup = new PopupMenu();
        tray = SystemTray.getSystemTray();
    }

    @PostConstruct
    private void setup() throws AWTException {
        final var item = new MenuItem("Exit");
        item.addActionListener(e -> eventPublisher.publishEvent(new ShutdownEvent()));
        popup.add(item);
        setPopupMenu(popup);
        setImageAutoSize(true);
        tray.add(this);
    }

    @Override
    public void destroy() {
        tray.remove(this);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = BotTrayIcon.class.getResource(path);
        if (imageURL == null) {
            System.err.println("Failed Creating Image. Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL, description).getImage();
        }
    }
}
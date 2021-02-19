package com.kayukin.vlcbot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener {
    private final ApplicationContext applicationContext;

    public ShutdownListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @EventListener
    public void onEvent(ShutdownEvent event) {
        final var exitCode = SpringApplication.exit(applicationContext, () -> 0);
        System.exit(exitCode);
    }

    public static class ShutdownEvent extends ApplicationEvent {
        public ShutdownEvent() {
            super("");
        }
    }
}

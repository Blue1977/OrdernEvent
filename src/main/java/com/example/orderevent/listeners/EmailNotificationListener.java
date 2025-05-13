package com.example.orderevent.listeners;

import com.example.orderevent.event.OrderCreatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {
    private static final Logger logger = LogManager.getLogger(EmailNotificationListener.class);

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        var order = event.getOrder();
        logger.info("Enviando email de confirmación a {} paar orden {} con productos {}", order.getEmail(), order.getId(), order.getProducts());
    }
}


package com.example.orderevent.listeners;

import com.example.orderevent.event.OrderCreatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditLogListener {
    private static final Logger logger = LogManager.getLogger(AuditLogListener.class);

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        var order = event.getOrder();
        logger.info("Grabando audit para orden {} con productos {}", order.getId(), order.getProducts());
    }
}

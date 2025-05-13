package com.example.orderevent.listeners;

import com.example.orderevent.event.OrderCreatedEvent;
import com.example.orderevent.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryUpdateListener {
    private static final Logger logger = LogManager.getLogger(InventoryUpdateListener.class);
    @Autowired
    private ProductRepository productRepository;

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        var order = event.getOrder();
        for (var product : order.getProducts()) {
            if (product.getStock() > 0) {
                product.setStock(product.getStock() - 1);
                productRepository.save(product);
                logger.info("Stock reducido {}. Stock disponible: {}", product.getName(), product.getStock());
            } else {
                logger.warn("Producto {} fuera de stock", product.getName());
            }
        }
    }
}

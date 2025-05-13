package com.example.orderevent.service;

import com.example.orderevent.entity.Order;
import com.example.orderevent.entity.Product;
import com.example.orderevent.event.OrderCreatedEvent;
import com.example.orderevent.repository.OrderRepository;
import com.example.orderevent.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class orderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public Order createOrder(Order order) {
        // Cargar completamente los productos desde la base de datos
        List<Product> productsWithStock = order.getProducts().stream()
                .map(product -> productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found")))
                .toList();
        order.setProducts(productsWithStock);

        // Guardar la orden con los productos completos
        Order savedOrder = orderRepository.save(order);

        // Publicar el evento de creación de la orden
        OrderCreatedEvent event = new OrderCreatedEvent(this, savedOrder);
        eventPublisher.publishEvent(event);

        return savedOrder;
    }
}

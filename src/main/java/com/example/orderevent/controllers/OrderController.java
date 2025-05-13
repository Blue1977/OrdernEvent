package com.example.orderevent.controllers;

import com.example.orderevent.entity.Order;
import com.example.orderevent.entity.Product;
import com.example.orderevent.event.OrderCreatedEvent;
import com.example.orderevent.repository.OrderRepository;
import com.example.orderevent.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        // Cargar completamente los productos desde la base de datos
        List<Product> productsWithStock = order.getProducts().stream()
                .map(product -> productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found")))
                .toList();
        order.setProducts(productsWithStock);

        // Guardar la orden con los productos completos
        orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(this, order);
        eventPublisher.publishEvent(event);

        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
    }
}

package com.example.orderevent.controllers;

import com.example.orderevent.entity.Order;
import com.example.orderevent.entity.Product;
import com.example.orderevent.event.OrderCreatedEvent;
import com.example.orderevent.repository.OrderRepository;
import com.example.orderevent.repository.ProductRepository;
import com.example.orderevent.service.orderService;
import com.example.orderevent.service.orderService;
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
    private orderService OrderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        // Delegar la creación de la orden al servicio
        OrderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
    }
}

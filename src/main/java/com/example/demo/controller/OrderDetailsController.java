package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OrderDetails;
import com.example.demo.repository.OrderDetailsRepository;

@RestController
@RequestMapping("/orderdetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @PostMapping("/upload")
    public ResponseEntity<OrderDetails> upload(@RequestBody OrderDetails orderDetails) {
        OrderDetails savedOrder = orderDetailsRepository.save(orderDetails);
        return ResponseEntity.ok(savedOrder);
    }
}


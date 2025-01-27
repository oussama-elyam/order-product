package org.yam.springbootorderproduct.controller;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dtoRequest.OrderDtoRequest;
import org.yam.springbootorderproduct.model.Order;
import org.yam.springbootorderproduct.service.OrderService;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RequestMapping("/api/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody OrderDtoRequest body, BindingResult result) {

        try {
            Order newOrder = new Order();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(body, newOrder);

            Order newOrderCreated = orderService.createOrder(newOrder);

            return new ResponseEntity<>(newOrderCreated, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the Order: " + e.getMessage());
        }

    }


    @GetMapping("/getPaggination")
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Order> Orders = orderService.getOrders(page, size);

        return new ResponseEntity<>(Orders.getContent(), HttpStatus.OK);
    }



    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateOrderSql(@RequestBody OrderDtoRequest body, @PathVariable("id") Long id) {

        Order existingOrder = new Order();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(body, existingOrder);

        existingOrder.setId(id); // Ensure the id remains unchanged

        Order updatedOrder = orderService.updateOrder(existingOrder, id);

        if (updatedOrder == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

}

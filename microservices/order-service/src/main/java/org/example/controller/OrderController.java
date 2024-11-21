package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.RequestOrder;
import org.example.dto.ResponseOrderDto;
import org.example.model.ResponseOrder;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody RequestOrder requesrtOrder) {
        orderService.placeOrder(requesrtOrder);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseOrder fetchOrders(){
        List<ResponseOrderDto> orders=orderService.fetchOrders();
        return ResponseOrder.builder().msg("Orders fetched successfully!!").respDtoList(orders).build();
    }
}

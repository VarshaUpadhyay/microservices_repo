package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderLineItemsDto;
import org.example.dto.ResponseOrderDto;
import org.example.dto.SkuDto;
import org.example.model.Order;
import org.example.model.OrderLineItems;
import org.example.model.RequestOrder;
import org.example.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepo orderRepo;
    private final WebClient.Builder webClient;
    private final ObjectMapper objectMapper;

    public void placeOrder(RequestOrder requestOrder) {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());

        Optional<String> args =requestOrder.getOrderLineItemsDto().stream().map(OrderLineItemsDto::getSkuCode)
                .reduce((a,b)->a+","+b);
        if(args.isPresent()) {
            List<SkuDto> list = objectMapper.convertValue(webClient.build().get()
                            .uri("http://inventory/api/inventory/" + args.get())
                            .retrieve().bodyToMono(List.class).block()
                    , new TypeReference<List<SkuDto>>() {
                    });

            List<OrderLineItems> orderLineItems = requestOrder.getOrderLineItemsDto().stream().map(a -> mapToOrderLineItem(a, list)).toList();
            order.setOrderLineItems(orderLineItems);
            orderRepo.save(order);
            log.info("Order {} created", order.getOrderNo());
        }
    }

    private OrderLineItems mapToOrderLineItem(OrderLineItemsDto orderLineItemsDto, List<SkuDto> list) {

        final OrderLineItems[] orderLineItems = new OrderLineItems[1];

        list.stream().filter(a->orderLineItemsDto.getSkuCode().equals(a.getSku()) && a.getAvailable())
                .map(a-> orderLineItems[0] =OrderLineItems.builder().price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity()).skuCode(orderLineItemsDto.getSkuCode()).build()).findFirst();

        return orderLineItems[0];
    }

    public List<ResponseOrderDto> fetchOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(this::mapToDto).toList();
    }

    private ResponseOrderDto mapToDto(Order order) {
        return ResponseOrderDto.builder().orderId(order.getOrderId()).orderNo(order.getOrderNo())
                .orderLineItems(order.getOrderLineItems()).build();

    }
}

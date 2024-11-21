package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.OrderLineItems;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderDto {
    private Long orderId;
    private String orderNo;
    private List<OrderLineItems> orderLineItems;
}

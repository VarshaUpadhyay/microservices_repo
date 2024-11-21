package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.OrderLineItemsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {
    private List<OrderLineItemsDto> orderLineItemsDto;
}

package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.OrderLineItemsDto;
import org.example.model.RequestOrder;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class OrderApplicationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }

    @Test
    public void shouldPlaceOrder() throws Exception {
        RequestOrder order = getOrder();
        String content = objectMapper.writeValueAsString(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated());
    }

    public RequestOrder getOrder() {
        RequestOrder order = new RequestOrder();
        order.setOrderLineItemsDto(List.of(OrderLineItemsDto.builder().price(BigDecimal.valueOf(12000)).skuCode("sk001").quantity(2).build()));
        return order;
    }
}

package com.practice.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.product.dto.ProductRequest;
import com.practice.product.dto.ProductResponse;
import com.practice.product.repository.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private ProductRepo repo;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}


	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequestBody();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON).content(productRequestString)).andExpect(status().isCreated());
		Assertions.assertEquals(1,repo.findAll().size());
	}

	private ProductRequest getProductRequestBody() {
		return ProductRequest.builder().name("IPhone").price(BigDecimal.valueOf(1200000)).description("Mobile").build();
	}

	//@Test
	void shouldFetchProduct() throws Exception {
		List<ProductResponse> productResponseList = getproductResponseList();
		String productResponseString = objectMapper.writeValueAsString(productResponseList);
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).andExpect(status().isOk()).andReturn();
		Assertions.assertLinesMatch(Stream.of(productResponseString),Stream.of(response.getResponse().getContentAsString()));
	}

	private List<ProductResponse> getproductResponseList() throws Exception {
		List<ProductResponse> productResponseList = new ArrayList<>();
		productResponseList.add(ProductResponse.builder().id("671fd444e71a3c62d35af852").name("IPhone").price(BigDecimal.valueOf(1200000)).description("Mobile").build());
		return productResponseList;
	}

}

package com.asphalt.gamingsystem;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void shouldReturnAllProducts() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Product("1", "Laptop", "Desc", "Cat", "tag", 1000, 5)));
        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value("1"));
    }
}
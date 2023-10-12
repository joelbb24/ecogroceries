package com.jardevs.ecogroceries.controller;

import com.jardevs.ecogroceries.entity.Cart;
import com.jardevs.ecogroceries.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest {

    @Mock
    private CartService cartService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CartController cartController = new CartController();
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void getAllOrders() throws Exception {
        List<Cart> orders = Arrays.asList(new Cart(), new Cart());

        when(cartService.getAllCarts()).thenReturn(orders);

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getOrderById() throws Exception {
        Long orderId = 1L;
        Cart order = new Cart();
        order.setId(orderId);

        when(cartService.getCartById(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));
    }

    @Test
    void createOrder() throws Exception {
        Cart order = new Cart();
        order.setId(1L);

        when(cartService.createCart(ArgumentMatchers.any(Cart.class))).thenReturn(order);

        String jsonRequest = "{ \"id\": 1 }";

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateOrder() throws Exception {
        Long orderId = 1L;
        Cart updatedOrder = new Cart();
        updatedOrder.setId(orderId);

        when(cartService.updateCart(eq(orderId), ArgumentMatchers.any(Cart.class))).thenReturn(updatedOrder);

        String jsonRequest = "{ \"id\": 1 }";

        mockMvc.perform(put("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteOrder() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteCart(eq(orderId));
    }
}

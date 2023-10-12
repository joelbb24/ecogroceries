package com.jardevs.ecogroceries.service;

import com.jardevs.ecogroceries.entity.Cart;
import com.jardevs.ecogroceries.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElse(null);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, Cart cart) {
        Optional<Cart> existingCart = cartRepository.findById(id);
        if (existingCart.isPresent()) {
            Cart updatedCart = existingCart.get();
            updatedCart.setUser(cart.getUser());
            updatedCart.setItems(cart.getItems());
            updatedCart.setTotalAmount(cart.getTotalAmount());
            updatedCart.setDelivered(cart.isDelivered());
            // Update other fields as needed
            return cartRepository.save(updatedCart);
        } else {
            return null; // Cart with the given ID not found
        }
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
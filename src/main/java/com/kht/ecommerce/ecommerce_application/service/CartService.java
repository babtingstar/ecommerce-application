package com.kht.ecommerce.ecommerce_application.service;

import com.kht.ecommerce.ecommerce_application.dto.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> getCartById(int userId);
}

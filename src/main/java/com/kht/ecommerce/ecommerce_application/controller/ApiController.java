package com.kht.ecommerce.ecommerce_application.controller;

import com.kht.ecommerce.ecommerce_application.dto.Cart;
import com.kht.ecommerce.ecommerce_application.dto.Product;
import com.kht.ecommerce.ecommerce_application.dto.User;
import com.kht.ecommerce.ecommerce_application.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartServiceImpl cartService;


// 사용자 목록 API
@GetMapping("/api/users")
public List<User> getUsers() {
    return userService.getAllUsers();
}

// 상품 목록 API
@GetMapping("/api/products")
public List<Product> getProducts() {
    return productService.getAllProduct();
}

/*
    HTTPS Status 500 - Internal Server Error 서버에서 생각지 못한 문제 발생
    Expected one result (or null) to be returned by selectOne(), bu found : 3
*/
// 특정 사용자의 장바구니 API
    // http://localhost:8080/api/carts?userId=1
@GetMapping("/api/carts")
public List<Cart> getCart(@RequestParam("userId") int userId) {
    return cartService.getCartById(userId);
}

@GetMapping("/cart{userId}")
    public String getCartByUserId(@PathVariable("userId") int userId) {
    return "cart";
}
}
package com.kht.ecommerce.ecommerce_application.mapper;

import com.kht.ecommerce.ecommerce_application.dto.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    // getCartByUserId
    List<Cart> getCartById(int userId);
}

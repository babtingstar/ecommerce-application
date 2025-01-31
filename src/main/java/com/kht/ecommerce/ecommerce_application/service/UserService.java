package com.kht.ecommerce.ecommerce_application.service;

import com.kht.ecommerce.ecommerce_application.dto.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    // 사용자 저장
    void insertUser(User user);
    User getByUserId(int id);
    // 이메일 존재 유무 확인
   // Map<String, Object> existByEmail(String email);
    boolean existByEmail(String email);
    int editUser(User user);
}

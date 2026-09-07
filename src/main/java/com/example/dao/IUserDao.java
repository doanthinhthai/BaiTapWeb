package com.example.dao;

import com.example.model.User;

public interface IUserDao {
    void insert(User user);
    void update(User user);
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    User checkLogin(String username, String password);
}
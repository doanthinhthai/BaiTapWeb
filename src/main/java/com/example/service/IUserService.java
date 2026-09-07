package com.example.service;

import com.example.model.User;

public interface IUserService {
    boolean register(String username, String email, String password, String fullname, String phone);
    boolean verifyOtp(String email, String otp);
    User login(String username, String password);
    boolean forgotPassword(String email);
    boolean resetPassword(String email, String otp, String newPassword);
}
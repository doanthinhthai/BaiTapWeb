package com.example.service;

import jakarta.servlet.http.HttpServletRequest;
import com.example.model.User;

public interface IUserService {
    // Đăng ký (nhận req để gửi mail)
    String register(HttpServletRequest req, String username, String email, String password, String fullname, String phone);

    // Kích hoạt OTP
    String verifyOtp(String email, String otp);

    // Đăng nhập
    User login(String username, String password);

    // Quên mật khẩu
    String forgotPassword(HttpServletRequest req, String email);

    // Đổi mật khẩu bằng OTP
    String resetPassword(String email, String otp, String newPassword);

    User findByUsername(String username);
    
    User findById(int id);
    boolean updateProfile(int id, String fullname, String phone, String images);
}
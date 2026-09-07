package com.example.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IEmailService {
	
    // Gửi email OTP kích hoạt đăng ký (kèm link xác nhận trực tiếp lấy từ HttpServletRequest)
    boolean sendActivationOtp(HttpServletRequest req, String toEmail, String fullname, String otp);

    // Gửi email OTP đặt lại mật khẩu
    boolean sendResetPasswordOtp(HttpServletRequest req, String toEmail, String fullname, String otp);
}
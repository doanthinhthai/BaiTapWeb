package com.example.service;

import java.util.Date;
import java.util.Random;
import jakarta.servlet.http.HttpServletRequest;
import com.example.dao.IUserDao;
import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.PasswordUtil;

public class UserServiceImpl implements IUserService {
    private final IUserDao userDao = new UserDao();
    private final IEmailService emailService = new EmailServiceImpl();

    @Override
    public String register(HttpServletRequest req, String username, String email, String password, String fullname, String phone) {
        // 1. Backend Validation
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ các trường bắt buộc!";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Định dạng email không hợp lệ!";
        }

        if (password.length() < 6) {
            return "Mật khẩu phải có tối thiểu 6 ký tự!";
        }

        // 2. Kiểm tra trùng lặp trong DB
        if (userDao.findByUsername(username) != null) {
            return "Tên đăng nhập đã tồn tại trong hệ thống!";
        }
        if (userDao.findByEmail(email) != null) {
            return "Email này đã được đăng ký tài khoản!";
        }

        // 3. Sinh OTP 6 số an toàn
        String otp = String.format("%06d", new Random().nextInt(1000000));
        Date expiry = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5 phút

        // 4. Lưu User với trạng thái CHƯA KÍCH HOẠT (status = 0) và mật khẩu đã băm SHA-256
        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim());
        user.setPassword(PasswordUtil.hashPassword(password)); // Băm mật khẩu
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRole(0);   // User thường
        user.setStatus(0); // Chờ kích hoạt OTP
        user.setOtp(otp);
        user.setOtpExpiry(expiry);

        userDao.insert(user);

        // 5. Gửi email qua EmailService sử dụng HttpServletRequest
        emailService.sendActivationOtp(req, email, fullname, otp);

        return "SUCCESS";
    }

    @Override
    public String verifyOtp(String email, String otp) {
        if (email == null || otp == null || otp.trim().isEmpty()) {
            return "Vui lòng nhập mã OTP!";
        }

        User user = userDao.findByEmail(email);
        if (user == null) {
            return "Không tìm thấy tài khoản với email này!";
        }

        if (user.getStatus() == 1) {
            return "Tài khoản đã được kích hoạt trước đó!";
        }

        // Kiểm tra mã OTP và thời gian hết hạn
        if (user.getOtp() == null || !user.getOtp().equals(otp.trim())) {
            return "Mã OTP không chính xác!";
        }

        if (user.getOtpExpiry().before(new Date())) {
            return "Mã OTP đã hết hạn! Vui lòng yêu cầu cấp lại.";
        }

        // Kích hoạt thành công -> Xóa mã OTP trong DB để không thể dùng lại
        user.setStatus(1);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDao.update(user);

        return "SUCCESS";
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null) return null;
        // Băm mật khẩu trước khi so khớp với DB
        String hashedPassword = PasswordUtil.hashPassword(password);
        return userDao.checkLogin(username, hashedPassword);
    }

    @Override
    public String forgotPassword(HttpServletRequest req, String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Vui lòng nhập email!";
        }

        User user = userDao.findByEmail(email.trim());
        if (user == null) {
            return "Email không tồn tại trong hệ thống!";
        }

        // Sinh OTP mới và lưu hạn 5 phút
        String otp = String.format("%06d", new Random().nextInt(1000000));
        user.setOtp(otp);
        user.setOtpExpiry(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
        userDao.update(user);

        // Gửi OTP qua EmailService
        emailService.sendResetPasswordOtp(req, email, user.getFullname(), otp);

        return "SUCCESS";
    }

    @Override
    public String resetPassword(String email, String otp, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            return "Mật khẩu mới phải có ít nhất 6 ký tự!";
        }

        User user = userDao.findByEmail(email);
        if (user == null || user.getOtp() == null || !user.getOtp().equals(otp.trim())) {
            return "Mã OTP không chính xác!";
        }

        if (user.getOtpExpiry().before(new Date())) {
            return "Mã OTP đã hết hiệu lực!";
        }

        // Cập nhật mật khẩu mới đã băm
        user.setPassword(PasswordUtil.hashPassword(newPassword));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDao.update(user);

        return "SUCCESS";
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
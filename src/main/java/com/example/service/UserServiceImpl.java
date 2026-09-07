package com.example.service;

import java.util.Date;
import com.example.dao.IUserDao;
import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.EmailUtil;

public class UserServiceImpl implements IUserService {
    private final IUserDao userDao = new UserDao();

    @Override
    public boolean register(String username, String email, String password, String fullname, String phone) {
        if (userDao.findByUsername(username) != null || userDao.findByEmail(email) != null) {
            return false;
        }

        String otp = EmailUtil.generateOtp();
        Date expiry = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5 phút

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRole(0);
        user.setStatus(0); // Chưa kích hoạt
        user.setOtp(otp);
        user.setOtpExpiry(expiry);

        userDao.insert(user);

        // Gửi OTP qua email
        String subject = "Xác nhận mã OTP kích hoạt tài khoản";
        String content = "<h3>Xin chào " + fullname + ",</h3>"
                + "<p>Mã OTP kích hoạt tài khoản của bạn là: <b style='color:red; font-size: 20px;'>" + otp + "</b></p>"
                + "<p>Mã này có hiệu lực trong 5 phút.</p>";
        EmailUtil.sendEmail(email, subject, content);
        return true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        User user = userDao.findByEmail(email);
        if (user != null && user.getOtp() != null && user.getOtp().equals(otp)) {
            if (user.getOtpExpiry().after(new Date())) {
                user.setStatus(1); // Kích hoạt tài khoản
                user.setOtp(null);
                user.setOtpExpiry(null);
                userDao.update(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        return userDao.checkLogin(username, password);
    }

    @Override
    public boolean forgotPassword(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            String otp = EmailUtil.generateOtp();
            user.setOtp(otp);
            user.setOtpExpiry(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
            userDao.update(user);

            String subject = "Khôi phục mật khẩu - Mã xác nhận OTP";
            String content = "<h3>Xin chào " + user.getFullname() + ",</h3>"
                    + "<p>Mã OTP để đặt lại mật khẩu là: <b style='color:green; font-size: 20px;'>" + otp + "</b></p>";
            return EmailUtil.sendEmail(email, subject, content);
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        User user = userDao.findByEmail(email);
        if (user != null && user.getOtp() != null && user.getOtp().equals(otp)) {
            if (user.getOtpExpiry().after(new Date())) {
                user.setPassword(newPassword);
                user.setOtp(null);
                user.setOtpExpiry(null);
                userDao.update(user);
                return true;
            }
        }
        return false;
    }
}
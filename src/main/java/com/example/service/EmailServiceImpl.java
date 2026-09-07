package com.example.service;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.HttpServletRequest;

public class EmailServiceImpl implements IEmailService {
	
    private static final String SENDER_EMAIL = "temporary8386@gmail.com";
    private static final String SENDER_PASSWORD = "uhetmcmlpkaynnzn\r\n"
    		+ "";

    @Override
    public boolean sendActivationOtp(HttpServletRequest req, String toEmail, String fullname, String otp) {
        // Backend tự trích xuất URL gốc của server từ request
        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String verifyUrl = baseUrl + "/verify-otp?email=" + toEmail;

        String subject = "Xác nhận mã OTP kích hoạt tài khoản";
        String content = "<h3>Xin chào " + fullname + ",</h3>"
                + "<p>Bạn vừa đăng ký tài khoản tại hệ thống của chúng tôi.</p>"
                + "<p>Mã OTP kích hoạt của bạn là: <b style='color:red; font-size: 24px; letter-spacing: 2px;'>" + otp + "</b></p>"
                + "<p>Mã có hiệu lực trong vòng <b>5 phút</b>.</p>"
                + "<p>Bạn có thể bấm vào link sau để đến trang nhập mã: <a href='" + verifyUrl + "'>" + verifyUrl + "</a></p>"
                + "<br><p>Trân trọng,<br>Ban Quản Trị Hệ Thống</p>";

        return sendMail(toEmail, subject, content);
    }

    @Override
    public boolean sendResetPasswordOtp(HttpServletRequest req, String toEmail, String fullname, String otp) {
        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String resetUrl = baseUrl + "/reset-password";

        String subject = "Yêu cầu khôi phục mật khẩu";
        String content = "<h3>Xin chào " + fullname + ",</h3>"
                + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>"
                + "<p>Mã OTP xác nhận là: <b style='color:green; font-size: 24px; letter-spacing: 2px;'>" + otp + "</b></p>"
                + "<p>Mã có hiệu lực trong vòng <b>5 phút</b>.</p>"
                + "<p>Trang đặt lại mật khẩu: <a href='" + resetUrl + "'>" + resetUrl + "</a></p>"
                + "<br><p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>";

        return sendMail(toEmail, subject, content);
    }

    private boolean sendMail(String toEmail, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Hệ thống Xác thực"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
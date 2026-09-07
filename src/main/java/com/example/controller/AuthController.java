package com.example.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.example.model.User;
import com.example.service.IUserService;
import com.example.service.UserServiceImpl;
import com.example.util.Constant;

@WebServlet(urlPatterns = {
    "/login", "/register", "/verify-otp", 
    "/forgot-password", "/reset-password", "/logout"
})
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/login")) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
                        req.setAttribute("username", c.getValue());
                        req.setAttribute("remember", true);
                        break;
                    }
                }
            }
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);

        } else if (uri.endsWith("/register")) {
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);

        } else if (uri.endsWith("/verify-otp")) {
            req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);

        } else if (uri.endsWith("/forgot-password")) {
            req.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(req, resp);

        } else if (uri.endsWith("/reset-password")) {
            req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);

        } else if (uri.endsWith("/logout")) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("account");
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        // 1. ĐĂNG KÝ
        if (uri.endsWith("/register")) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            boolean success = userService.register(username, email, password, fullname, phone);
            if (success) {
                req.getSession().setAttribute("verifyEmail", email);
                resp.sendRedirect(req.getContextPath() + "/verify-otp");
            } else {
                req.setAttribute("error", "Username hoặc Email đã tồn tại!");
                req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            }

        // 2. KÍCH HOẠT OTP
        } else if (uri.endsWith("/verify-otp")) {
            String email = (String) req.getSession().getAttribute("verifyEmail");
            if (email == null) email = req.getParameter("email");
            String otp = req.getParameter("otp");

            if (userService.verifyOtp(email, otp)) {
                req.getSession().removeAttribute("verifyEmail");
                req.setAttribute("message", "Kích hoạt tài khoản thành công! Vui lòng đăng nhập.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Mã OTP không chính xác hoặc đã quá hạn!");
                req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
            }

        // 3. ĐĂNG NHẬP VỚI SESSION & COOKIE
        } else if (uri.endsWith("/login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");

            User user = userService.login(username, password);

            if (user != null) {
                if (user.getStatus() != 1) {
                    req.setAttribute("error", "Tài khoản chưa kích hoạt OTP hoặc đã bị khóa!");
                    req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                    return;
                }

                // Lưu Session
                HttpSession session = req.getSession();
                session.setAttribute("account", user);

                // Lưu Cookie nếu chọn Remember
                Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
                if ("on".equals(remember)) {
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                } else {
                    cookie.setMaxAge(0);
                }
                cookie.setPath("/");
                resp.addCookie(cookie);

                if (user.getRole() == 1) {
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
            } else {
                req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            }

        // 4. QUÊN MẬT KHẨU GỬI OTP
        } else if (uri.endsWith("/forgot-password")) {
            String email = req.getParameter("email");
            if (userService.forgotPassword(email)) {
                req.getSession().setAttribute("resetEmail", email);
                resp.sendRedirect(req.getContextPath() + "/reset-password");
            } else {
                req.setAttribute("error", "Email không tồn tại trong hệ thống!");
                req.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(req, resp);
            }

        // 5. ĐẶT LẠI MẬT KHẨU BẰNG OTP
        } else if (uri.endsWith("/reset-password")) {
            String email = (String) req.getSession().getAttribute("resetEmail");
            String otp = req.getParameter("otp");
            String newPassword = req.getParameter("newPassword");

            if (userService.resetPassword(email, otp, newPassword)) {
                req.getSession().removeAttribute("resetEmail");
                req.setAttribute("message", "Đổi mật khẩu thành công! Hãy đăng nhập lại.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn!");
                req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);
            }
        }
    }
}
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
            // Lấy username từ Cookie nếu có để hiển thị lại
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
            // Nhận email từ query param nếu bấm trực tiếp từ link trong email
            String emailParam = req.getParameter("email");
            if (emailParam != null) {
                req.getSession().setAttribute("verifyEmail", emailParam);
            }
            req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);

        } else if (uri.endsWith("/forgot-password")) {
            req.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(req, resp);

        } else if (uri.endsWith("/reset-password")) {
            req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);

        } else if (uri.endsWith("/logout")) {
            // Hủy session ở Backend
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

        // 1. LUỒNG ĐĂNG KÝ
        if (uri.endsWith("/register")) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            // Truyền trực tiếp req vào service để backend tự build link email
            String result = userService.register(req, username, email, password, fullname, phone);

            if ("SUCCESS".equals(result)) {
                req.getSession().setAttribute("verifyEmail", email);
                resp.sendRedirect(req.getContextPath() + "/verify-otp");
            } else {
                req.setAttribute("error", result);
                req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            }

        // 2. LUỒNG KÍCH HOẠT MÃ OTP
        } else if (uri.endsWith("/verify-otp")) {
            String email = (String) req.getSession().getAttribute("verifyEmail");
            if (email == null) email = req.getParameter("email");
            String otp = req.getParameter("otp");

            String result = userService.verifyOtp(email, otp);

            if ("SUCCESS".equals(result)) {
                req.getSession().removeAttribute("verifyEmail");
                req.setAttribute("message", "Tài khoản của bạn đã được kích hoạt thành công! Hãy đăng nhập.");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", result);
                req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
            }

        // 3. LUỒNG ĐĂNG NHẬP (SESSION + COOKIE)
        } else if (uri.endsWith("/login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");

            User user = userService.login(username, password);

            if (user != null) {
                if (user.getStatus() != 1) {
                    req.setAttribute("error", "Tài khoản chưa được kích hoạt mã OTP hoặc đã bị khóa!");
                    req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
                    return;
                }

                // Lưu User vào Session
                HttpSession session = req.getSession();
                session.setAttribute("account", user);

                // Xử lý Cookie Remember Me ở Backend
                Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
                if ("on".equals(remember)) {
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                } else {
                    cookie.setMaxAge(0); // Xóa cookie nếu không tick
                }
                cookie.setPath("/");
                cookie.setHttpOnly(true); // Bảo mật tránh tấn công XSS từ client
                resp.addCookie(cookie);

                // Điều hướng theo Role
                if (user.getRole() == 1) {
                    resp.sendRedirect(req.getContextPath() + "/admin/products");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
            } else {
                req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            }

        // 4. LUỒNG QUÊN MẬT KHẨU
        } else if (uri.endsWith("/forgot-password")) {
            String email = req.getParameter("email");
            String result = userService.forgotPassword(req, email);

            if ("SUCCESS".equals(result)) {
                req.getSession().setAttribute("resetEmail", email);
                resp.sendRedirect(req.getContextPath() + "/reset-password");
            } else {
                req.setAttribute("error", result);
                req.getRequestDispatcher("/views/auth/forgot-password.jsp").forward(req, resp);
            }

        // 5. LUỒNG ĐẶT LẠI MẬT KHẨU MỚI
        } else if (uri.endsWith("/reset-password")) {
            String email = (String) req.getSession().getAttribute("resetEmail");
            String otp = req.getParameter("otp");
            String newPassword = req.getParameter("newPassword");

            String result = userService.resetPassword(email, otp, newPassword);

            if ("SUCCESS".equals(result)) {
                req.getSession().removeAttribute("resetEmail");
                req.setAttribute("message", "Mật khẩu đã được đặt lại thành công!");
                req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", result);
                req.getRequestDispatcher("/views/auth/reset-password.jsp").forward(req, resp);
            }
        }
    }
}
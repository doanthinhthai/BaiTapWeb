package com.example.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import com.example.model.User;
import com.example.service.IUserService;
import com.example.service.UserServiceImpl;
import com.example.util.Constant;

@WebFilter(urlPatterns = { "/admin/*" })
public class AuthFilter implements Filter {
    private final IUserService userService = new UserServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("account");

        // 1. Nếu chưa có Session, kiểm tra Cookie Remember Me ở Backend
        if (user == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
                        String username = c.getValue();
                        user = userService.findByUsername(username);
                        if (user != null && user.getStatus() == 1) {
                            session.setAttribute("account", user); // Tự động phục hồi Session
                        }
                        break;
                    }
                }
            }
        }

        // 2. Kiểm tra quyền truy cập vào /admin/*
        if (user == null) {
            // Chưa đăng nhập -> đá về trang Login
            resp.sendRedirect(req.getContextPath() + "/login?error=unauthorized");
            return;
        }

        if (user.getRole() != 1) {
            // Đã đăng nhập nhưng không phải Admin (role != 1) -> đá về trang chủ kèm lỗi
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang Quản trị!");
            return;
        }

        // Hợp lệ thì cho đi tiếp vào Servlet
        chain.doFilter(request, response);
    }
}
package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.example.model.User;
import com.example.service.IUserService;
import com.example.service.UserServiceImpl;
import com.example.util.Constant;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 5,         // Tối đa 5 MB
    maxRequestSize = 1024 * 1024 * 10      // Tối đa 10 MB
)
@WebServlet(urlPatterns = { "/profile", "/profile/update" })
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("account") : null;

        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy dữ liệu mới nhất từ CSDL
        User user = userService.findById(sessionUser.getId());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/web/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("account") : null;

        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        Map<String, String> errors = new HashMap<>();

        // 1. VALIDATION PHÍA BACKEND
        if (fullname == null || fullname.trim().isEmpty()) {
            errors.put("fullname", "Họ và tên không được để trống!");
        } else if (fullname.trim().length() < 2 || fullname.trim().length() > 100) {
            errors.put("fullname", "Họ và tên phải từ 2 đến 100 ký tự!");
        }

        if (phone != null && !phone.trim().isEmpty()) {
            // Validate định dạng số điện thoại Việt Nam (10 số, bắt đầu bằng 03, 05, 07, 08, 09)
            if (!phone.trim().matches("^(0|\\+84)[35789][0-9]{8}$")) {
                errors.put("phone", "Số điện thoại không hợp lệ (Ví dụ hợp lệ: 0987654321)!");
            }
        }

        // 2. XỬ LÝ UPLOAD ẢNH BẰNG MULTIPART
        Part part = req.getPart("imageFile");
        String fname = null;
        if (part != null && part.getSize() > 0) {
            String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int dotIndex = submittedFileName.lastIndexOf(".");
            if (dotIndex > 0) {
                String ext = submittedFileName.substring(dotIndex + 1).toLowerCase();
                // Validate định dạng file ảnh
                if (!ext.matches("jpg|jpeg|png|gif|webp")) {
                    errors.put("images", "Chỉ chấp nhận file ảnh định dạng JPG, JPEG, PNG, GIF, WEBP!");
                } else {
                    File uploadDir = new File(Constant.DIR);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    fname = "user_" + sessionUser.getId() + "_" + System.currentTimeMillis() + "." + ext;
                    part.write(Constant.DIR + "/" + fname);
                }
            } else {
                errors.put("images", "File ảnh không hợp lệ!");
            }
        }

        // NẾU CÓ LỖI VALIDATION -> TRẢ VỀ FORM KÈM THÔNG BÁO
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            User userView = userService.findById(sessionUser.getId());
            userView.setFullname(fullname);
            userView.setPhone(phone);
            req.setAttribute("user", userView);
            req.getRequestDispatcher("/views/web/profile.jsp").forward(req, resp);
            return;
        }

        // NẾU HỢP LỆ -> CẬP NHẬT QUA JPA
        userService.updateProfile(sessionUser.getId(), fullname.trim(), phone != null ? phone.trim() : "", fname);

        // Cập nhật lại Session để giao diện SiteMesh tự đổi thông tin ngay
        User updatedUser = userService.findById(sessionUser.getId());
        session.setAttribute("account", updatedUser);

        req.setAttribute("message", "Cập nhật hồ sơ cá nhân thành công!");
        req.setAttribute("user", updatedUser);
        req.getRequestDispatcher("/views/web/profile.jsp").forward(req, resp);
    }
}
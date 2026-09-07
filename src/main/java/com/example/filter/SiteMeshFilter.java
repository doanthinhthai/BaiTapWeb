package com.example.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(filterName = "sitemesh", urlPatterns = "/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // 1. Xóa tiền tố mặc định /WEB-INF/ để SiteMesh tìm thẳng vào thư mục /decorators/ có sẵn của bạn
        builder.setDecoratorPrefix("");

        // 2. Áp dụng 01 Template Bootstrap duy nhất (/decorators/web.jsp)
        builder.addDecoratorPath("/*", "/decorators/web.jsp");

        // 3. Loại trừ trang Admin (để trang admin giữ nguyên giao diện quản trị riêng)
        builder.addExcludedPath("/admin/*");

        // 4. Loại trừ các trang Auth, ảnh và tài nguyên tĩnh
        builder.addExcludedPath("/login*")
               .addExcludedPath("/register*")
               .addExcludedPath("/verify-otp*")
               .addExcludedPath("/forgot-password*")
               .addExcludedPath("/reset-password*")
               .addExcludedPath("/image*")
               .addExcludedPath("/static/*")
               .addExcludedPath("/assets/*");
    }
}
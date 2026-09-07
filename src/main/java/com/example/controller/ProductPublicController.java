package com.example.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.model.Product;
import com.example.service.IProductService;
import com.example.service.ProductServiceImpl;

@WebServlet(urlPatterns = { "/product", "/product/detail" })
public class ProductPublicController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // 1. Xem chi tiết sản phẩm
        if (uri.endsWith("/product/detail")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/views/web/product-detail.jsp").forward(req, resp);

        // 2. Phân trang 6 sản phẩm / trang
        } else {
            int pageSize = 6;
            int page = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {}
            }

            List<Product> list = productService.findAll(page - 1, pageSize);
            int totalProducts = productService.count();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            req.setAttribute("productList", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);

            req.getRequestDispatcher("/views/web/product-list.jsp").forward(req, resp);
        }
    }
}
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

@WebServlet(urlPatterns = { "/home", "" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy 10 sản phẩm mới nhất
        List<Product> top10 = productService.findTop10Recent();
        req.setAttribute("top10Products", top10);
        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
    }
}
package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.example.model.Category;
import com.example.model.Product;
import com.example.service.ICategoryService;
import com.example.service.IProductService;
import com.example.service.CategoryServiceImpl;
import com.example.service.ProductServiceImpl;
import com.example.util.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert", "/admin/product/edit",
		"/admin/product/update", "/admin/product/delete" })
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IProductService productService = new ProductServiceImpl();
	private final ICategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		if (url.contains("/admin/products")) {
			List<Product> list = productService.findAll();
			req.setAttribute("listproduct", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/add")) {
			List<Category> listCategory = categoryService.findAll();
			req.setAttribute("categories", listCategory);
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product product = productService.findById(id);
			List<Category> listCategory = categoryService.findAll();
			req.setAttribute("product", product);
			req.setAttribute("categories", listCategory);
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				productService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		File uploadDir = new File(Constant.DIR);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		// ==========================================
		// 1. XỬ LÝ THÊM MỚI SẢN PHẨM (INSERT)
		// ==========================================
		if (url.contains("/admin/product/insert")) {
			String name = req.getParameter("productName");
			String priceStr = req.getParameter("price");
			String quantityStr = req.getParameter("quantity");
			String description = req.getParameter("description");
			String statusStr = req.getParameter("status");
			String categoryIdStr = req.getParameter("categoryId");

			Map<String, String> errors = new HashMap<>();
			double price = 0;
			int quantity = 0;
			int categoryId = 0;
			int status = 1;

			// --- VALIDATION PHÍA SERVER ---
			if (name == null || name.trim().isEmpty()) {
				errors.put("productName", "Tên sản phẩm không được để trống!");
			}

			try {
				price = Double.parseDouble(priceStr);
				if (price <= 0) {
					errors.put("price", "Đơn giá phải lớn hơn 0 VNĐ!");
				}
			} catch (Exception e) {
				errors.put("price", "Đơn giá không hợp lệ!");
			}

			try {
				quantity = Integer.parseInt(quantityStr);
				if (quantity < 0) {
					errors.put("quantity", "Số lượng tồn kho không được âm!");
				}
			} catch (Exception e) {
				errors.put("quantity", "Số lượng phải là số nguyên!");
			}

			try {
				categoryId = Integer.parseInt(categoryIdStr);
				if (categoryId <= 0) {
					errors.put("categoryId", "Vui lòng chọn danh mục cho sản phẩm!");
				}
			} catch (Exception e) {
				errors.put("categoryId", "Vui lòng chọn một danh mục hợp lệ!");
			}

			if (statusStr != null) {
				try {
					status = Integer.parseInt(statusStr);
				} catch (Exception ignored) {
				}
			}

			// NẾU CÓ LỖI VALIDATION -> QUAY LẠI TRANG THÊM KÈM THÔNG BÁO
			if (!errors.isEmpty()) {
				req.setAttribute("errors", errors);
				req.setAttribute("categories", categoryService.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}

			// NẾU DỮ LIỆU HỢP LỆ -> LƯU VÀO DATABASE
			Product p = new Product();
			p.setProductName(name.trim());
			p.setPrice(price);
			p.setQuantity(quantity);
			p.setDescription(description);
			p.setStatus(status);
			p.setCategory(categoryService.findById(categoryId));

			Part part = req.getPart("images");
			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				String fname = "prod_" + System.currentTimeMillis() + "." + ext;
				part.write(Constant.DIR + "/" + fname);
				p.setImages(fname);
			} else {
				p.setImages("default.png");
			}

			productService.insert(p);
			resp.sendRedirect(req.getContextPath() + "/admin/products");

			// ==========================================
			// 2. XỬ LÝ CẬP NHẬT SẢN PHẨM (UPDATE)
			// ==========================================
		} else if (url.contains("/admin/product/update")) {
			int productId = Integer.parseInt(req.getParameter("productId"));
			String name = req.getParameter("productName");
			String priceStr = req.getParameter("price");
			String quantityStr = req.getParameter("quantity");
			String description = req.getParameter("description");
			String statusStr = req.getParameter("status");
			String categoryIdStr = req.getParameter("categoryId");

			Map<String, String> errors = new HashMap<>();
			double price = 0;
			int quantity = 0;
			int categoryId = 0;
			int status = 1;

			if (name == null || name.trim().isEmpty()) {
				errors.put("productName", "Tên sản phẩm không được để trống!");
			}

			try {
				price = Double.parseDouble(priceStr);
				if (price <= 0)
					errors.put("price", "Đơn giá phải lớn hơn 0 VNĐ!");
			} catch (Exception e) {
				errors.put("price", "Đơn giá không hợp lệ!");
			}

			try {
				quantity = Integer.parseInt(quantityStr);
				if (quantity < 0)
					errors.put("quantity", "Số lượng tồn kho không được âm!");
			} catch (Exception e) {
				errors.put("quantity", "Số lượng phải là số nguyên!");
			}

			try {
				categoryId = Integer.parseInt(categoryIdStr);
			} catch (Exception e) {
				errors.put("categoryId", "Vui lòng chọn danh mục!");
			}

			if (statusStr != null) {
				try {
					status = Integer.parseInt(statusStr);
				} catch (Exception ignored) {
				}
			}

			// NẾU CÓ LỖI VALIDATION -> QUAY LẠI TRANG SỬA
			if (!errors.isEmpty()) {
				req.setAttribute("errors", errors);
				Product productView = productService.findById(productId);
				productView.setProductName(name);
				productView.setPrice(price);
				productView.setQuantity(quantity);
				productView.setDescription(description);
				req.setAttribute("product", productView);
				req.setAttribute("categories", categoryService.findAll());
				req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
				return;
			}

			// NẾU HỢP LỆ -> UPDATE
			Product p = productService.findById(productId);
			p.setProductName(name.trim());
			p.setPrice(price);
			p.setQuantity(quantity);
			p.setDescription(description);
			p.setStatus(status);
			p.setCategory(categoryService.findById(categoryId));

			Part part = req.getPart("images");
			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				String fname = "prod_" + System.currentTimeMillis() + "." + ext;
				part.write(Constant.DIR + "/" + fname);
				p.setImages(fname);
			}

			productService.update(p);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}
}
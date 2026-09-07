package com.example.dao;

import java.util.List;
import com.example.model.Product;

public interface IProductDao {
    void insert(Product product);
    void update(Product product);
    void delete(int productId) throws Exception;
    Product findById(int productId);
    List<Product> findAll();
    List<Product> findTop10Recent();               // 10 SP mới nhất
    List<Product> findAll(int page, int pageSize);  // Phân trang 6 sp/trang
    int count();
}
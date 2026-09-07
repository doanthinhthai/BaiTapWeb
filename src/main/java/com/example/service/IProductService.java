package com.example.service;

import java.util.List;
import com.example.model.Product;

public interface IProductService {
    void insert(Product product);
    void update(Product product);
    void delete(int productId) throws Exception;
    Product findById(int productId);
    List<Product> findAll();
    List<Product> findTop10Recent();
    List<Product> findAll(int page, int pageSize);
    int count();
}
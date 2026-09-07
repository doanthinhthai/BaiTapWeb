package com.example.service;

import java.util.List;
import com.example.dao.IProductDao;
import com.example.dao.ProductDao;
import com.example.model.Product;

public class ProductServiceImpl implements IProductService {
    private final IProductDao productDao = new ProductDao();

    @Override
    public void insert(Product product) { productDao.insert(product); }

    @Override
    public void update(Product product) { productDao.update(product); }

    @Override
    public void delete(int productId) throws Exception { productDao.delete(productId); }

    @Override
    public Product findById(int productId) { return productDao.findById(productId); }

    @Override
    public List<Product> findAll() { return productDao.findAll(); }

    @Override
    public List<Product> findTop10Recent() { return productDao.findTop10Recent(); }

    @Override
    public List<Product> findAll(int page, int pageSize) { return productDao.findAll(page, pageSize); }

    @Override
    public int count() { return productDao.count(); }
}
package com.example.service;
import com.example.model.Category;
import java.util.List;

public interface ICategoryService {
    void insert(Category category);
    void update(Category category);
    void delete(int cateid) throws Exception;
    Category findById(int cateid);
    List<Category> findAll();
}
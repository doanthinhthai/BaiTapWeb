package com.example.dao;
import com.example.model.Category;
import java.util.List;

public interface ICategoryDao {
    void insert(Category category);
    void update(Category category);
    void delete(int cateid) throws Exception;
    Category findById(int cateid);
    List<Category> findAll();
}
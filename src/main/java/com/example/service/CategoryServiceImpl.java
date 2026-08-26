package com.example.service;
import com.example.dao.CategoryDao;
import com.example.dao.ICategoryDao;
import com.example.model.Category;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    public ICategoryDao cateDao = new CategoryDao();

    @Override
    public void insert(Category category) { cateDao.insert(category); }
    @Override
    public void update(Category category) { cateDao.update(category); }
    @Override
    public void delete(int id) throws Exception { cateDao.delete(id); }
    @Override
    public Category findById(int id) { return cateDao.findById(id); }
    @Override
    public List<Category> findAll() { return cateDao.findAll(); }
}
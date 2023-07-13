package com.teamsix.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.item.Category;
import com.teamsix.model.dao.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
    private  CategoryRepository categoryRepository;

    

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Category createCategory(Category category) {
        Category save = categoryRepository.save(category);
        categoryRepository.flush();	
        return save;
    }
    @Transactional
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}

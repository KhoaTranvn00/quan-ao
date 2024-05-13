package com.example.quanlycuahang.service;

import com.example.quanlycuahang.dto.CategoryDetail;
import com.example.quanlycuahang.entity.Category;
import com.example.quanlycuahang.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryDetail getCategoryById(Long id) {
        Category c = categoryRepository.findById(id).orElse(null);
        CategoryDetail cd = new CategoryDetail(c.getName(), c.getProducts().size());
        return cd;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return categoryRepository.save(category);
        }
        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

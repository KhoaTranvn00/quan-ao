package com.example.quanlycuahang.service;

import com.example.quanlycuahang.dto.ProductDto;
import com.example.quanlycuahang.entity.Category;
import com.example.quanlycuahang.entity.Product;
import com.example.quanlycuahang.mapper.ProductMapper;
import com.example.quanlycuahang.repository.CategoryRepository;
import com.example.quanlycuahang.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(ProductDto productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: "));

        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDto productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Category category = categoryRepository.findById(productRequest.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: "));

        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

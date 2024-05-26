package com.example.quanlycuahang.dto;

import com.example.quanlycuahang.entity.Category;
import com.example.quanlycuahang.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDetail {
    private String name;
    private List<Product> products;
}

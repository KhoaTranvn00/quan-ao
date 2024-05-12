package com.example.quanlycuahang.dto;

import com.example.quanlycuahang.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDetail {
    private Category category;
    private int productCount;
}

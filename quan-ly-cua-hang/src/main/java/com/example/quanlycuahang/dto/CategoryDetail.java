package com.example.quanlycuahang.dto;

import com.example.quanlycuahang.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDetail {
    private String name;
    private int productCount;
}

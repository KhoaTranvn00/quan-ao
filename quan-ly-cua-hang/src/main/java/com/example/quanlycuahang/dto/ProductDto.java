package com.example.quanlycuahang.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String image;
    private String description;
    private double price;
    private boolean status;
    private long category_id;
}

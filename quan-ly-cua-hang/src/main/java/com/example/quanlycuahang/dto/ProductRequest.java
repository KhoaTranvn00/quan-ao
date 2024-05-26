package com.example.quanlycuahang.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private int quantity;
    private double price;

    // Constructors, getters, and setters
}

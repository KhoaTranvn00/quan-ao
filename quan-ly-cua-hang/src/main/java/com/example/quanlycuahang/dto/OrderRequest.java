package com.example.quanlycuahang.dto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {

    private LocalDate date;
    private Boolean status;
    private Long customerId;
    private List<ProductRequest> products;



    // Constructors, getters, and setters
}

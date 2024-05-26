package com.example.quanlycuahang.repository;

import com.example.quanlycuahang.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
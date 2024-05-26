package com.example.quanlycuahang.repository;

import com.example.quanlycuahang.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDate(LocalDate date);
    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
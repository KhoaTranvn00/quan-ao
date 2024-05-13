package com.example.quanlycuahang.repository;

import com.example.quanlycuahang.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

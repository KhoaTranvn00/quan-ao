package com.example.quanlycuahang.dto;

import com.example.quanlycuahang.entity.Customer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    private LocalDate date;
    private String session;
    private String type;
    private String status;
    private long customer_id;
}

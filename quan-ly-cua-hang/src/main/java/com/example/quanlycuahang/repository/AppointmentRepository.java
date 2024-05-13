package com.example.quanlycuahang.repository;

import com.example.quanlycuahang.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

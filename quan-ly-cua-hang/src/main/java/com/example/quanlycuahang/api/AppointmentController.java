package com.example.quanlycuahang.api;

import com.example.quanlycuahang.dto.AppointmentDto;
import com.example.quanlycuahang.entity.Appointment;
import com.example.quanlycuahang.entity.Category;
import com.example.quanlycuahang.entity.Customer;
import com.example.quanlycuahang.entity.Product;
import com.example.quanlycuahang.repository.AppointmentRepository;
import com.example.quanlycuahang.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElse(null);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        Customer customer = customerRepository.findById(appointmentDto.getCustomer_id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: "));

        appointment.setCustomer(customer);
        Appointment createdAppointment = appointmentRepository.save(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElse(null);

        appointment.setDate(appointmentDetails.getDate());
        appointment.setSession(appointmentDetails.getSession());
        appointment.setType(appointmentDetails.getType());
        appointment.setStatus(appointmentDetails.getStatus());
        appointment.setCustomer(appointmentDetails.getCustomer());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElse(null);

        appointmentRepository.delete(appointment);
        return ResponseEntity.noContent().build();
    }
}
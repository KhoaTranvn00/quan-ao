package com.example.quanlycuahang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@ToString(exclude = {"customer", "orderProducts"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @Transient
    private Double totalPrice;

    public double getTotalPrice() {
        return orderProducts.stream()
                .mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity())
                .sum();
    }

    // Constructors, getters, and setters

    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                '}';
    }

    // hashCode and equals methods (excluding orderProducts and customer to avoid recursion)
    public int hashCode() {
        // Use id for hashCode, or a more comprehensive set of fields if id is not set yet
        return (id != null ? id.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return id != null && id.equals(order.id);
    }
}


package com.example.quanlycuahang.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "order")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    private int quantity;
    private String name;
    private double price;
    private double totalPrice;

    // Constructors, getters, and setters

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    // hashCode and equals methods (excluding order to avoid recursion)
    @Override
    public int hashCode() {
        // Use id for hashCode, or a more comprehensive set of fields if id is not set yet
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderProduct orderProduct = (OrderProduct) obj;
        return id != null && id.equals(orderProduct.id);
    }
}


package com.example.quanlycuahang.api;

import com.example.quanlycuahang.dto.IncomeResponse;
import com.example.quanlycuahang.dto.OrderRequest;
import com.example.quanlycuahang.dto.ProductRequest;
import com.example.quanlycuahang.entity.Customer;
import com.example.quanlycuahang.entity.Order;
import com.example.quanlycuahang.entity.OrderProduct;
import com.example.quanlycuahang.entity.Product;
import com.example.quanlycuahang.repository.CustomerRepository;
import com.example.quanlycuahang.repository.OrderProductRepository;
import com.example.quanlycuahang.repository.OrderRepository;
import com.example.quanlycuahang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElse(null);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElse(null);

        Order order = new Order();
        order.setDate(orderRequest.getDate());
        order.setStatus(orderRequest.getStatus());
        order.setCustomer(customer);

        for (ProductRequest productRequest : orderRequest.getProducts()) {
            Product product = productRepository.findById(productRequest.getId())
                    .orElse(null);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setName(productRequest.getName());
            orderProduct.setQuantity(productRequest.getQuantity());
            orderProduct.setPrice(productRequest.getPrice());
            orderProduct.setOrder(order);

            order.getOrderProducts().add(orderProduct);
        }

        Order createdOrder = orderRepository.save(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        Order order = orderRepository.findById(id)
                .orElse(null);

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElse(null);

        order.setDate(orderRequest.getDate());
        order.setStatus(orderRequest.getStatus());
        order.setCustomer(customer);

        order.getOrderProducts().clear();
        for (ProductRequest productRequest : orderRequest.getProducts()) {
            Product product = productRepository.findById(productRequest.getId())
                    .orElse(null);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productRequest.getQuantity());
            orderProduct.setPrice(productRequest.getPrice());
            orderProduct.setOrder(order);

            order.getOrderProducts().add(orderProduct);
        }

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElse(null);

        orderRepository.delete(order);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/income/{date}")
    public IncomeResponse income(@PathVariable LocalDate date) {
        int month = date.getMonthValue();
        int year = date.getYear();

        double dateIncome = getTotalIncomeForDay(date);
        double monthIncome = getTotalIncomeForMonth(year, month);
        double yearIncome = getTotalIncomeForYear(year);

        return new IncomeResponse(dateIncome, monthIncome, yearIncome);
    }

    public double getTotalIncomeForDay(LocalDate date) {
        List<Order> orders = orderRepository.findByDate(date);
        return calculateTotalIncome(orders);
    }

    public double getTotalIncomeForMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Order> orders = orderRepository.findByDateBetween(startDate, endDate);
        return calculateTotalIncome(orders);
    }

    public double getTotalIncomeForYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<Order> orders = orderRepository.findByDateBetween(startDate, endDate);
        return calculateTotalIncome(orders);
    }

    private double calculateTotalIncome(List<Order> orders) {
        return orders.stream()
                .flatMapToDouble(order -> order.getOrderProducts().stream()
                        .mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity()))
                .sum();
    }
}
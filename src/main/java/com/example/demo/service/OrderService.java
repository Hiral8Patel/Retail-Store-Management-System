package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // =========================
    // SAVE ORDER
    // =========================
    public Order saveOrder(Order order) {

        Customer customer = customerRepository
                .findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());

        Double total = 0.0;

        for (OrderItem item : order.getOrderItems()) {

            Product product = productRepository
                    .findById(item.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            // Reduce stock
            product.setQuantity(product.getQuantity() - item.getQuantity());

            // Attach full product object
            item.setProduct(product);

            // Set price from product
            item.setPrice(product.getPrice());

            // Link order reference
            item.setOrder(order);

            total += product.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    // =========================
    // GET ALL ORDERS  ✅ NEW METHOD
    // =========================
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderController(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/add")
    @Transactional
    public Order createOrder(@RequestBody Order order) {

        if (order.getCustomer() == null ||
                order.getCustomer().getCustomerId() == null) {
            throw new IllegalArgumentException("A valid customer ID is required.");
        }

        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("At least one order item is required.");
        }

        Long customerId = order.getCustomer().getCustomerId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found: " + customerId));

        double total = 0.0;

        for (OrderItem item : order.getOrderItems()) {
            if (item.getProduct() == null ||
                    item.getProduct().getProductId() == null) {
                throw new IllegalArgumentException("A valid product ID is required.");
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }

            Long productId = item.getProduct().getProductId();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Product not found: " + productId));

            if (product.getQuantity() == null ||
                    product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getProductName());
            }

            if (product.getPrice() == null || product.getPrice() < 0) {
                throw new IllegalArgumentException(
                        "Invalid price for product: " + product.getProductName());
            }

            // Use the database price rather than a price supplied by the browser.
            item.setPrice(product.getPrice());
            item.setProduct(product);
            item.setOrder(order);

            total += product.getPrice() * item.getQuantity();

            // Reduce stock. The transaction rolls back if order saving fails.
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}

package com.minifood.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private FoodClient foodClient;

    @Autowired
    private UserClient userClient;

    public Order create(Order order) {

        userClient.validateUser(order.getUserId());

        double total = 0;

        for (OrderItem item : order.getItems()) {
            Map<String, Object> food = foodClient.getFood(item.getFoodId());
            total += ((Number) food.get("price")).doubleValue() * item.getQuantity();
        }

        order.setTotal(total);
        order.setStatus("CREATED");

        return repo.save(order);
    }

    public List<Order> getAll() {
        return repo.findAll();
    }

    public void updateStatus(Long id, String status) {
        Order order = repo.findById(id).orElseThrow();
        order.setStatus(status);
        repo.save(order);
    }
}

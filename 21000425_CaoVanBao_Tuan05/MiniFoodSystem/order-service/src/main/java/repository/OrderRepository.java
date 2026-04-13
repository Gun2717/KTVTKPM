package com.minifood.orderservice;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private Map<Long, Order> db = new HashMap<>();
    private AtomicLong idGen = new AtomicLong(1);

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGen.getAndIncrement());
        }
        db.put(order.getId(), order);
        return order;
    }

    public List<Order> findAll() {
        return new ArrayList<>(db.values());
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }
}

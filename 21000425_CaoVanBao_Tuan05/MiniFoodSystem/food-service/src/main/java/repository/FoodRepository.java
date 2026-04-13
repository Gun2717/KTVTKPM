package com.minifood.foodservice;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FoodRepository {

    private Map<Long, Food> db = new HashMap<>();
    private AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        save(new Food(null, "Phở", 40000));
        save(new Food(null, "Cơm tấm", 30000));
    }

    public Food save(Food food) {
        if (food.getId() == null) {
            food.setId(idGen.getAndIncrement());
        }
        db.put(food.getId(), food);
        return food;
    }

    public List<Food> findAll() {
        return new ArrayList<>(db.values());
    }

    public Optional<Food> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    public void delete(Long id) {
        db.remove(id);
    }
}

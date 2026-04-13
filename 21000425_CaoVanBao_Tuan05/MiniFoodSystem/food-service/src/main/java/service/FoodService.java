package com.minifood.foodservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository repo;

    public List<Food> getAll() {
        return repo.findAll();
    }

    public Food create(Food food) {
        return repo.save(food);
    }

    public Food update(Long id, Food food) {
        food.setId(id);
        return repo.save(food);
    }

    public void delete(Long id) {
        repo.delete(id);
    }

    public Food getById(Long id) {
        return repo.findById(id).orElseThrow();
    }
}

package com.minifood.userservice;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private Map<Long, User> db = new HashMap<>();
    private AtomicLong idGen = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGen.getAndIncrement());
        }
        db.put(user.getId(), user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        return db.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }
}

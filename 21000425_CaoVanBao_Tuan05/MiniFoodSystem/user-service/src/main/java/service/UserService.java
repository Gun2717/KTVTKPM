package com.minifood.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User register(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }
        user.setRole("USER");
        return repo.save(user);
    }

    public User login(User req) {
        return repo.findByUsername(req.getUsername())
                .filter(u -> u.getPassword().equals(req.getPassword()))
                .orElseThrow(() -> new RuntimeException("Đăng nhập thất bại"));
    }

    public List<User> getAll() {
        return repo.findAll();
    }
}

package com.minifood.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class UserClient {

    private final RestTemplate rest = new RestTemplate();
    private final String url;

    public UserClient(@Value("${user.service.url}") String url) {
        this.url = url;
    }

    public void validateUser(Long userId) {
        List<Map<String, Object>> users = rest.getForObject(url, List.class);
        boolean exists = users != null && users.stream().anyMatch(u -> userId.equals(((Number) u.get("id")).longValue()));
        if (!exists) {
            throw new RuntimeException("Không tìm thấy người dùng: " + userId);
        }
    }
}

package com.minifood.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FoodClient {

    private final RestTemplate rest = new RestTemplate();
    private final String url;

    public FoodClient(@Value("${food.service.url}") String url) {
        this.url = url;
    }

    public Map getFood(Long id) {
        return rest.getForObject(url + "/" + id, Map.class);
    }
}

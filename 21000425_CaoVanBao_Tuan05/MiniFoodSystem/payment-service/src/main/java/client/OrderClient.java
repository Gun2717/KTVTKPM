package com.minifood.paymentservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {

    private final RestTemplate rest = new RestTemplate();
    private final String url;

    public OrderClient(@Value("${order.service.url}") String url) {
        this.url = url;
    }

    public void updateStatus(Long orderId) {
        rest.put(url + "/" + orderId + "/status?status=PAID", null);
    }
}

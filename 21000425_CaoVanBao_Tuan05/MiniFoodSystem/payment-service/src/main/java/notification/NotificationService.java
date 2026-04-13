package com.minifood.paymentservice;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notify(String msg) {
        System.out.println("NOTIFICATION: " + msg);
    }
}

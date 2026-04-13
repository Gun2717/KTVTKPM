package com.minifood.paymentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private NotificationService notificationService;

    public String pay(Payment payment) {
        orderClient.updateStatus(payment.getOrderId());
        String msg = "Người dùng A đã đặt đơn #" + payment.getOrderId() + " thành công - phương thức: " + payment.getMethod();
        notificationService.notify(msg);
        return msg;
    }
}

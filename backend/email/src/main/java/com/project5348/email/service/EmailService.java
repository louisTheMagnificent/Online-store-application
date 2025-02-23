package com.project5348.email.service;

import com.project5348.email.util.LogUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @RabbitListener(queues = "order.queue")
    public void receiveMessage(String message) {
        LogUtil.logInfo("Received email: " + message);
    }
}
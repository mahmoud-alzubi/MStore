package com.mtech.notifications;

import com.mtech.commons.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationsTopic")
    public void handleNotification(OrderPlacedEvent placedEvent) {
        log.info("Notification received for order: {}", placedEvent);
        // send email notifications..
    }

}

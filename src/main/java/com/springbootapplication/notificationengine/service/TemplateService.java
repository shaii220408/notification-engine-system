package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.enums.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@Slf4j
public class TemplateService {

    public String renderTemplate(String type, Channel channel, Map<String, String> payload) {
        if (payload == null || payload.isEmpty()) {
            return getDefaultMessage(type);
        }

        String template = getDefaultMessage(type);
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

    private String getDefaultMessage(String type) {
        return switch (type) {
            case "ORDER_CONFIRMED" -> "Your order has been confirmed! Thank you for shopping with us.";
            case "REMINDER" -> "This is a reminder for your upcoming event.";
            case "ALERT" -> "Important alert: Please take immediate action.";
            case "WELCOME" -> "Welcome! We are glad to have you on board.";
            default -> "You have a new notification.";
        };
    }
}
package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    // Spring Boot автоматически отправляет все внутренние ошибки 404/500 на путь /error
    @RequestMapping("/error")
    public Map<String, Object> handleError() {
        Map<String, Object> response = new LinkedHashMap<>();
        
        response.put("message", "Uuups, this page is still not created...");
        response.put("documentation", "Find the full guide in our repository: https://gitlab.com/evgen.klimanow/my-spring-backend");
        
        // Шпаргалка по командам для пользователя
        response.put("hint_1_create_user", "curl -X POST http://5.35.28.230:8085/users -H \"Content-Type: application/json\" -d '{\"name\": \"EvgenKli\", \"email\": \"devops@example.com\"}'");
        response.put("hint_2_get_users", "curl http://5.35.28.230:8085/users");
        
        return response;
    }
}

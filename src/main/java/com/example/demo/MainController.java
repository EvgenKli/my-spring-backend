package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    // Метод обрабатывает чистый корень сайта
    @GetMapping("/")
    public String index() {
        return "Welcome to EvgenKli Production Cluster, a armyane akhueli!";
    }
}

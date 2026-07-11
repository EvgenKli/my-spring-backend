package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users") // Все запросы на http://.../users полетят сюда
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Обрабатываем GET-запрос: выдать всех пользователей из облачной базы
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Обрабатываем POST-запрос: принять нового пользователя в формате JSON и сохранить в облако
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
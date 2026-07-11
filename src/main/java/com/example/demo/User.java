package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users") // Java сама создаст таблицу с именем users в Postgres
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоматический счетчик ID (1, 2, 3...)
    private Long id;

    private String name;
    private String email;

    // Пустой конструктор (обязателен для JPA базы данных)
    public User() {}

    // Геттеры и сеттеры, чтобы Spring мог читать и записывать данные
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
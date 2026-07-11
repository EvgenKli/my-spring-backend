package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Здесь пусто! Все методы работы с PostgreSQL уже унаследованы из JpaRepository
}
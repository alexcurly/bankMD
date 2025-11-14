package com.bank.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс Spring Boot приложения модуля авторизации
 * Запускает весь контекст Spring и автоматически настраивает компоненты
 */
@SpringBootApplication
public class AuthorizationApplication {

    /**
     * Точка входа в приложение
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Запускает Spring Boot приложение
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
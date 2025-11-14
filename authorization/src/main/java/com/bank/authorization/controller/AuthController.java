package com.bank.authorization.controller;

import com.bank.authorization.dto.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов аутентификации
 * Управляет страницами логина и доступа
 */
@Controller
public class AuthController {

    /**
     * Отображает страницу логина
     *
     * @param error параметр запроса - наличие ошибки логина
     * @param logout параметр запроса - успешный логаут
     * @param model модель для передачи данных в представление
     * @return имя шаблона login.html
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        // Если была ошибка логина - добавляем сообщение
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }

        // Если был успешный логаут - добавляем сообщение
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }

        // Добавляем пустой объект для формы логина
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    /**
     * Отображает страницу "Доступ запрещен"
     *
     * @return имя шаблона access-denied.html
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}

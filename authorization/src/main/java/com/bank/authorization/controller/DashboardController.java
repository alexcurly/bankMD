package com.bank.authorization.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для панелей управления разных ролей
 * Каждая роль имеет свою панель с соответствующим функционалом
 */
@Controller
public class DashboardController {

    /**
     * Панель управления для клиента
     *
     * @param user аутентифицированный пользователь (внедряется Spring Security)
     * @param model модель для передачи данных в представление
     * @return имя шаблона client-dashboard.html
     */
    @GetMapping("/client")
    public String clientPanel(@AuthenticationPrincipal User user, Model model) {
        // Добавляем данные пользователя в модель
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", "CLIENT");
        return "client-dashboard";
    }

    /**
     * Панель управления для оператора
     */
    @GetMapping("/operator")
    public String operatorPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", "OPERATOR");
        return "operator-dashboard";
    }

    /**
     * Панель управления для аналитика антифрода
     */
    @GetMapping("/fraud")
    public String fraudPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", "FRAUD ANALYST");
        return "fraud-dashboard";
    }

    /**
     * Панель управления для администратора
     */
    @GetMapping("/admin")
    public String adminPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", "ADMIN");
        return "admin-dashboard";
    }
}
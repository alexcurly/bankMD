package com.bank.authorization.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Обработчик успешной аутентификации
 * Определяет, куда перенаправить пользователя после логина в зависимости от его роли
 */
@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Вызывается после успешной аутентификации пользователя
     *
     * @param request HTTP запрос
     * @param response HTTP ответ
     * @param authentication объект аутентификации с информацией о пользователе
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Определяем URL для редиректа на основе роли пользователя
        String redirectUrl = determineTargetUrl(authentication);
        // Выполняем перенаправление
        response.sendRedirect(redirectUrl);
    }

    /**
     * Определяет целевой URL для перенаправления на основе ролей пользователя
     *
     * @param authentication объект аутентификации
     * @return URL для перенаправления
     */
    private String determineTargetUrl(Authentication authentication) {
        // Перебираем все роли пользователя
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String authority = auth.getAuthority();
            // В зависимости от роли возвращаем соответствующий URL
            switch (authority) {
                case "ROLE_ADMIN":
                    return "/admin";          // Администратор -> панель администратора
                case "ROLE_FRAUD_ANALYST":
                    return "/fraud";          // Аналитик антифрода -> панель антифрода
                case "ROLE_OPERATOR":
                    return "/operator";       // Оператор -> панель оператора
                case "ROLE_CLIENT":
                    return "/client";         // Клиент -> панель клиента
            }
        }
        // Если ни одна роль не подошла - исключение
        throw new IllegalStateException("User has no valid role assigned");
    }
}

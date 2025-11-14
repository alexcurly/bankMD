package com.bank.authorization.config;

import com.bank.authorization.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Основная конфигурация Spring Security
 * Определяет правила доступа, аутентификации и авторизации
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler successHandler;

    /**
     * Конструктор с внедрением зависимостей
     */
    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          AuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    /**
     * Определяет цепочку фильтров безопасности
     * Настройка правила доступа, форму логина, логаут и обработку ошибок
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка авторизации запросов
                .authorizeRequests(auth -> auth
                        // Публичные URL - доступны без аутентификации
                        .antMatchers("/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .antMatchers("/access-denied").permitAll()
                        // Защищенные URL с проверкой ролей
                        .antMatchers("/admin/**").hasRole("ADMIN")                    // Только ADMIN
                        .antMatchers("/fraud/**").hasAnyRole("FRAUD_ANALYST", "ADMIN") // FRAUD_ANALYST или ADMIN
                        .antMatchers("/operator/**").hasAnyRole("OPERATOR", "ADMIN")  // OPERATOR или ADMIN
                        .antMatchers("/client/**").hasAnyRole("CLIENT", "ADMIN")      // CLIENT или ADMIN
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                // Настройка формы логина
                .formLogin(form -> form
                        .loginPage("/login")                    // Страница логина
                        .loginProcessingUrl("/perform_login")   // URL для обработки формы логина
                        .successHandler(successHandler)         // Обработчик успешного логина
                        .failureUrl("/login?error=true")        // URL при ошибке логина
                        .permitAll()                            // Разрешить доступ к форме логина всем
                )
                // Настройка логаута
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")           // URL для логаута
                        .logoutSuccessUrl("/login?logout=true") // URL после успешного логаута
                        .deleteCookies("JSESSIONID")            // Удалить сессионную куку
                        .invalidateHttpSession(true)            // Инвалидировать сессию
                        .permitAll()                            // Разрешить логаут всем
                )
                // Обработка исключений (доступ запрещен)
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")     // Страница при отказе в доступе
                )
                // Настройка провайдера аутентификации
                .authenticationProvider(authenticationProvider())
                // Временно отключаем CSRF для упрощения разработки
                // В продакшене обязательно включить!
                .csrf().disable();

        return http.build();
    }

    /**
     * Настраивает DaoAuthenticationProvider для работы с нашей UserDetailsService
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Устанавливаем наш кастомный сервис для загрузки пользователей
        authProvider.setUserDetailsService(userDetailsService);
        // Устанавливаем кодировщик паролей
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Бин для кодирования паролей (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
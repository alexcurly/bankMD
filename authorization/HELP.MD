# 🏦 HELP.MD — Spring MVC (Session) Security + PostgreSQL (CustomUserDetailsService)

Этот документ описывает: структуру схем PostgreSQL, конфигурацию Spring Boot (Session-based, Spring MVC + Spring Security), реализацию `CustomUserDetailsService` с использованием таблиц `auth.users` и `auth.roles`, а также контроллеры, шаблоны и последовательность действий для перенаправления пользователей на панели по ролям (`/client`, `/operator`, `/fraud`, `/admin`).

---

## Содержание
1. Краткая архитектура БД
2. SQL: схемы и таблицы (PostgreSQL)
3. Spring Boot: зависимости и структура проекта
4. Spring Security: конфигурация (Session-based)
5. Реализация `CustomUserDetailsService`
6. Контроллеры, Success Handler, страницы и маршрутизация
7. Репозитории JPA (примеры)
8. Примеры HTML-шаблонов (Thymeleaf)
9. Рекомендации по безопасности и развертыванию

---

## 1. Краткая архитектура БД

Схемы в одной базе `postgres` (PostgreSQL):
- `auth` — пользователи и роли
- `profile` — данные клиентов
- `account` — счета
- `transfer` — переводы
- `anti_fraud` — подозрительные операции
- `history` — история/аудит
- `public_info` — публичная информация о банке

(См. полный `init_schemas.sql` в проекте для создания схем и таблиц.)

---

## 2. SQL: схемы и таблицы (основное)

Пример таблиц для авторизации (PostgreSQL):

```sql
-- Используем auth вместо authorization
CREATE SCHEMA IF NOT EXISTS auth;

-- Создание остальных схем
CREATE SCHEMA IF NOT EXISTS profile;
CREATE SCHEMA IF NOT EXISTS account;
CREATE SCHEMA IF NOT EXISTS transfer;
CREATE SCHEMA IF NOT EXISTS anti_fraud;
CREATE SCHEMA IF NOT EXISTS history;
CREATE SCHEMA IF NOT EXISTS public_bank_information;

-- Создание таблиц в схеме auth
CREATE TABLE auth.roles (
                            id BIGSERIAL PRIMARY KEY,
                            role_name VARCHAR(255) UNIQUE NOT NULL,
                            description TEXT
);

CREATE TABLE auth.users (
                            id BIGSERIAL PRIMARY KEY,
                            username VARCHAR(255) UNIQUE NOT NULL,
                            password_hash VARCHAR(255) NOT NULL,
                            role_id BIGINT NOT NULL REFERENCES auth.roles(id),
                            profile_id BIGINT,
                            is_enabled BOOLEAN DEFAULT true,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Наполнение ролями
INSERT INTO auth.roles (role_name, description) VALUES
                                                    ('client', 'Клиент банка'),
                                                    ('operator', 'Оператор поддержки'),
                                                    ('fraud_analyst', 'Аналитик антифрода'),
                                                    ('admin', 'Администратор');

-- Создание тестового пользователя
INSERT INTO auth.users (username, password_hash, role_id, profile_id, is_enabled) VALUES
    ('admin', '$2a$12$ABC123DEF456GHI789JKL.MNO1PQRST2UVWXYZ3ABCDEF4GHIJKLMNOP', 4, 1, true);

```

> Пароли в `auth.users.password_hash` должны храниться захэшированными (BCrypt).

---

## 3. Spring Boot: зависимости и структура проекта

В `build.gradle` / `pom.xml` добавь минимальный набор зависимостей:

**Gradle (fragment)**:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
runtimeOnly 'org.postgresql:postgresql'
implementation 'org.springframework.boot:spring-boot-starter-validation'
```
Убедись, что используешь Java 17+ и Spring Boot 3.x.

### Структура пакетов (рекомендуется)

```
com.example.bankapp
├─ config         (SecurityConfig, WebMvcConfig)
├─ controller     (AuthController, DashboardController)
├─ model          (UserEntity, RoleEntity, AccountEntity и т.д.)
├─ repository     (UserRepository, RoleRepository, AccountRepository)
├─ service        (CustomUserDetailsService, UserService)
├─ dto            (LoginDto и т.п.)
└─ templates      (thymeleaf: login.html, client.html, operator.html, fraud.html, admin.html)
```

---

## 4. Spring Security: конфигурация (Session-based)

Пример `SecurityConfig.java` (Spring Boot 3, Java 17):

```java
package com.bank.authorization.config;

import com.bank.authorization.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          AuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // для форм можно оставить enabled, но потребуются CSRF-токены в шаблонах
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/access-denied").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/fraud/**").hasAnyRole("FRAUD_ANALYST","ADMIN")
                .requestMatchers("/operator/**").hasAnyRole("OPERATOR","ADMIN")
                .requestMatchers("/client/**").hasAnyRole("CLIENT","ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout=true")
            )
            .userDetailsService(userDetailsService)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

> Примечание: `csrf.disable()` временно отключен для простоты. В продакшне CSRF должен быть включён для форм или использоваться CSRF-токены в шаблонах.

---

## 5. Реализация `CustomUserDetailsService`

**UserEntity** (схема `auth`):
```java
package com.bank.authorization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "auth")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    // getters/setters
}
```

**RoleEntity**:
```java
package com.bank.authorization.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles", schema = "auth")
public class RoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;
    private String description;

    // getters/setters, equals/hashCode
}
```

**UserRepository**:
```java
package com.bank.authorization.repository;

import com.bank.authorization.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
```

**CustomUserDetailsService**:
```java
package com.bank.authorization.service;

import com.bank.authorization.model.UserEntity;
import com.bank.authorization.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authority)
                .disabled(!Boolean.TRUE.equals(user.getIsEnabled()))
                .build();
    }
}
```

---

## 6. Контроллеры, Success Handler и маршрутизация

**AuthenticationSuccessHandler** — выбирает цель редиректа по роли:

```java
package com.bank.authorization.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = "/";
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/admin";
                break;
            } else if (role.equals("ROLE_FRAUD_ANALYST")) {
                redirectUrl = "/fraud";
                break;
            } else if (role.equals("ROLE_OPERATOR")) {
                redirectUrl = "/operator";
                break;
            } else if (role.equals("ROLE_CLIENT")) {
                redirectUrl = "/client";
                break;
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
```

**AuthController** (страница логина/доступа):
```java
package com.bank.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
```

**DashboardController** — панели по ролям (простые примеры):
```java
package com.bank.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

@Controller
public class DashboardController {

    @GetMapping("/client")
    public String clientPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "client";
    }

    @GetMapping("/operator")
    public String operatorPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "operator";
    }

    @GetMapping("/fraud")
    public String fraudPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "fraud";
    }

    @GetMapping("/admin")
    public String adminPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "admin";
    }
}
```

---

## 7. Репозитории JPA (примеры)
```java
// RoleRepository
package com.bank.authorization.repository;
import com.bank.authorization.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
```

---

## 8. Примеры HTML-шаблонов (Thymeleaf)

**templates/login.html**:
```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
</head>
<body>
  <h2>Login</h2>
  <form th:action="@{/perform_login}" method="post">
    <div><label>Username: <input type="text" name="username"/></label></div>
    <div><label>Password: <input type="password" name="password"/></label></div>
    <div><button type="submit">Sign In</button></div>
    <div th:if="${param.error}">Invalid credentials.</div>
    <div th:if="${param.logout}">You have been logged out.</div>
  </form>
</body>
</html>
```

**templates/access-denied.html**:
```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<head><meta charset="UTF-8"><title>Access denied</title></head>
<body>
  <h2>Access Denied</h2>
  <p>You do not have permission to access this resource.</p>
  <a th:href="@{/login}">Login</a>
</body>
</html>
```

**templates/client.html** (пример):
```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<head><meta charset="UTF-8"><title>Client Panel</title></head>
<body>
  <h2>Client Panel</h2>
  <p>Welcome, <span th:text="${username}"></span></p>
  <a th:href="@{/perform_logout}">Logout</a>
</body>
</html>
```

---

## 9. Рекомендации по безопасности и развертыванию

- Всегда храните пароли в виде BCrypt-хэша (`BCryptPasswordEncoder`).  
- В продакшне включите CSRF-защиту и используйте CSRF-токены в формах.  
- Ограничьте доступ к административным схемам и таблицам через сетевые ACL и RBAC.  
- Логи и audit: каждое изменение в `profile`, `account`, `transfer` должно писать запись в `history` (триггеры или application-level logging).  
- Для масштабирования сессий используйте Redis-backed Spring Session.  
- Настройте HTTPS и HSTS; cookie должны быть Secure и HttpOnly.  
- Протестируй поведение ролей: убедись, что `ROLE_ADMIN` может видеть всё, `ROLE_CLIENT` — только свои данные.

---

## 10. Быстрый пример создания пользователя через SQL (BCrypt)
```sql
INSERT INTO auth.users (username, password_hash, role_id, profile_id)
VALUES ('ivan.petrov', '$2a$12$...bcrypthash...', 1, NULL);
```

---

## 11. Полезные команды и отладка
- Просмотр пользователей: `SELECT id, username, role_id, is_enabled FROM auth.users;`  
- Включи `logging.level.org.springframework.security=DEBUG` для диагностики проблем аутентификации/авторизации.

---

© 2025 BankingApp Documentation — Spring MVC (Session) Edition

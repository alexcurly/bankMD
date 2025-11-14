package com.bank.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация Spring MVC
 * Настраивает обработку статических ресурсов и простые контроллеры представлений
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Настройка простых контроллеров представлений (без логики)
     * Используется для статических страниц, которые не требуют обработки данных
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Корневой URL перенаправляет на страницу логина
        registry.addViewController("/").setViewName("redirect:/login");
        // URL /login отображает страницу login.html
        registry.addViewController("/login").setViewName("login");
        // URL /access-denied отображает страницу access-denied.html
        registry.addViewController("/access-denied").setViewName("access-denied");
    }

    /**
     * Настройка обработчиков статических ресурсов
     * Определяет, где искать CSS, JS, изображения и другие статические файлы
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // CSS файлы: /css/** -> classpath:/static/css/
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        // JavaScript файлы: /js/** -> classpath:/static/js/
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        // Изображения: /images/** -> classpath:/static/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        // WebJars (Bootstrap, jQuery и др.): /webjars/** -> classpath:/META-INF/resources/webjars/
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
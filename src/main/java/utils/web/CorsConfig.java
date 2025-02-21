package utils.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Разрешаем CORS для всех эндпоинтов /api
                        .allowedOrigins("https://matveykk.github.io", "http://localhost:63342") // Разрешаем запросы с этих доменов
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешаем методы
                        .allowedHeaders("*") // Разрешаем все заголовки
                        .allowCredentials(true); // Разрешаем передачу куки и авторизационных данных
            }
        };
    }
}

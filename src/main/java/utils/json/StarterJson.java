package utils.json;

import bot.FarmBot;
import database.WorkWithDB;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
public class StarterJson {
    @GetMapping("/data") // Обрабатываем GET-запрос на /api/data
    public MyData getData() {
        // Создаем объект с данными
        MyData data = new MyData();
        data.setCarrots(WorkWithDB.getUserCarrots(FarmBot.username));
        return data; // Spring Boot автоматически преобразует объект в JSON
    }

    // Внутренний класс для хранения данных
    public static class MyData {
        private int carrots;

        public int getCarrots() {
            return carrots;
        }

        public void setCarrots(int carrots) {
            this.carrots = carrots;
        }
    }
}

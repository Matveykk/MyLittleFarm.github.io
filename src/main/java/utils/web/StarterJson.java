package utils.web;

import database.WorkWithDB;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StarterJson {
    @GetMapping("/data") // Обрабатываем GET-запрос на /api/data
    public MyData getData() {
        // Создаем объект с данными
        MyData data = new MyData();

        while (CurrUser.username == null) {
            System.out.println("wait for username");
        }

        System.out.println("getData: " + CurrUser.username);
        data.setCarrots(WorkWithDB.getUserCarrots(CurrUser.username));
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

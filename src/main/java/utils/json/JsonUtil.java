package utils.json;

import bot.FarmBot;
import database.WorkWithDB;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342") // Разрешаем запросы с этого домена
@RestController
@RequestMapping("/api")
public class JsonUtil {

    @PostMapping("/endpoint")
    public ResponseEntity<MyData> handleJson(@RequestBody MyData myData) {
        System.out.println("Полученные данные на сервере: " + myData);

        // Проверяем, что поле carrots не null
        if (myData.getCarrots() == null || myData.getCarrots() < 0) {
            System.out.println("Ошибка: параметр carrots некорректен.");
            return ResponseEntity.badRequest().body(new MyData(0)); // Возвращаем ответ с ошибкой
        }
        //Записываем в БД текущее количество морковок
        WorkWithDB.updateCarrotCount(FarmBot.username, myData.getCarrots());
        return ResponseEntity.ok(myData);
    }

    // Класс для хранения данных
    public static class MyData {
        private Integer carrots; // Используем Integer для поддержки null

        public MyData() {}

        public MyData(Integer carrots) {
            this.carrots = carrots;
        }

        public Integer getCarrots() {
            return carrots;
        }

        public void setCarrots(Integer carrots) {
            this.carrots = carrots;
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "carrots=" + carrots +
                    '}';
        }
    }
}

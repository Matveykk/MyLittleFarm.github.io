package utils.json;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342") // Разрешаем запросы с этого домена
@RestController
@RequestMapping("/api")
public class JsonUtil {

    @PostMapping("/endpoint")
    public ResponseEntity<MyData> handleJson(@RequestBody MyData myData) {
        System.out.println("Received data: " + myData); // Логируем полученные данные
        if (myData.getCarrots() == null) {
            System.out.println("Bad request: carrots is null"); // Логируем ошибку
            return ResponseEntity.badRequest().build();
        }
        MyData myData1 = new MyData(12);
        return ResponseEntity.ok(myData1);
    }

    public static class MyData {
        private Integer carrots; // Используем Integer для поддержки null

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
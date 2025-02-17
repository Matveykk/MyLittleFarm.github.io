package botClient;

import DataBaseWork.WorkWithDB;
import com.google.gson.Gson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resourcesUtil.PropertiesUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.google.gson.Gson;

import java.util.Random;

public class FarmBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "bot.token";
    private static final String BOT_USERNAME = "bot.username";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendMessage(chatId);
                    break;
                case "/help":
                    sendHelpMessage(chatId);
                    break;
                default:
                    sendDefaultMessage(chatId);
            }
        }

        // Обработка данных из веб-приложения
        if (update.hasMessage() && update.getMessage().getWebAppData() != null) {
            WebAppData webAppData = update.getMessage().getWebAppData();
            String data = webAppData.getData(); // Получаем данные
            long chatId = update.getMessage().getChatId();

            try {
                // Парсим JSON
                Gson gson = new Gson();
                WebAppPayload payload = gson.fromJson(data, WebAppPayload.class);

                // Обрабатываем данные
                int carrots = payload.getCarrots();

                // Отправляем ответ пользователю
                System.out.println("Вы вырастили " + carrots + " морковок! Ваш ID: ");
            } catch (Exception e) {
                sendMessage(chatId, "Ошибка обработки данных: " + e.getMessage());
            }
        }
    }

        // Метод для отправки приветственного сообщения
        private void sendMessage ( long chatId){
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));

            Random random = new Random();
            String[] Answers = {"Hello! Let's plant\nIf you need some - /help\nOne more message - /start",
                    "Welcome to the Farm!\nIf you need some - /help\nOne more message - /start",
                    "Hello! Let's farm some wheat\nIf you need some - /help\nOne more message - /start"};
            message.setText(Answers[random.nextInt(Answers.length)]);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        // Метод для отправки сообщения с помощью
        private void sendHelpMessage ( long chatId){
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("In process...");

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        // Метод для отправки сообщения по умолчанию (если команда не распознана)
        private void sendDefaultMessage ( long chatId){
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Я не понимаю эту команду. Используйте \"Помощь\"");

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        // Метод для отправки сообщения с данными из веб-приложения
        private void sendMessage ( long chatId, String text){
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(text);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String getBotUsername () {
            return PropertiesUtil.get(BOT_USERNAME);
        }

        @Override
        public String getBotToken () {
            return PropertiesUtil.get(BOT_TOKEN);
        }

        private static class WebAppPayload {
            private int carrots;
            private long userId;

            public int getCarrots() {
                return carrots;
            }
        }
    }
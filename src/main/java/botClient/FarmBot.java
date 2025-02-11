package botClient;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resourcesUtil.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;

// Основной класс бота, который наследует TelegramLongPollingBot
public class FarmBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "bot.token";
    private static final String BOT_USERNAME = "bot.username";

    // Метод, который вызывается при получении нового сообщения от пользователя
    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли в сообщении текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Получаем текст сообщения и ID чата
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Обрабатываем команды
            switch (messageText) {
                case "Информация":
                    sendStartMessage(chatId);
                    break;
                case "Помощь":
                    sendHelpMessage(chatId);
                    break;
                default:
                    sendDefaultMessage(chatId);
            }
        }
    }

    // Метод для отправки приветственного сообщения
    private void sendStartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Добро пожаловать! Выберите действие:");

        // Создаем клавиатуру для кнопок
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Создаем первую строку кнопок
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Помощь");
        row1.add("Информация");

        // Добавляем строку в клавиатуру
        keyboard.add(row1);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для отправки сообщения с помощью
    private void sendHelpMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Это простой бот-пример. Доступные команды:\n\"Информация\" - начать\n\"Помощь\" - помощь");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для отправки сообщения по умолчанию (если команда не распознана)
    private void sendDefaultMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Я не понимаю эту команду. Используйте \"Помощь\"");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод, возвращающий имя бота (должно совпадать с именем, которое вы указали в BotFather)
    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOT_USERNAME);
    }

    // Метод, возвращающий токен бота (полученный от BotFather)
    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOT_TOKEN);
    }
}

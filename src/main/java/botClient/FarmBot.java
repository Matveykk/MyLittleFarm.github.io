package botClient;

import DataBaseWork.WorkWithDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resourcesUtil.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Основной класс бота
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

        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.equals("START_BUTTON")) {
                sendMessage(chatId);
            } else if (callbackData.equals("HELP_BUTTON")) {
                sendHelpMessage(chatId);
            } else if (callbackData.equals("APP_BUTTON")) {
                // Ничего
            }
        }

        // Обработка данных из веб-приложения
        if (update.hasMessage() && update.getMessage().getWebAppData() != null) {
            String webAppData = update.getMessage().getWebAppData().getData(); // Получаем данные
            long chatId = update.getMessage().getChatId();

            // Обработка данных (пример: парсим JSON)
            try {
                // Предположим, что данные приходят в формате JSON: {"carrots": 5}
                int carrotCount = Integer.parseInt(webAppData.split(":")[1].replace("}", "").trim());
                sendMessage(chatId, "Вы вырастили " + carrotCount + " морковок!");
            } catch (Exception e) {
                sendMessage(chatId, "Ошибка обработки данных: " + e.getMessage());
            }
        }
    }

    // Метод для отправки приветственного сообщения
    private void sendMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        setKeyboardUnderMessage(chatId, message);

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
    private void sendHelpMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        setKeyboardUnderMessage(chatId, message);
        message.setText("In process...");

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

    // Метод для отправки сообщения с данными из веб-приложения
    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для создания клавиатуры под сообщением
    private void setKeyboardUnderMessage(long chatId, SendMessage message) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        InlineKeyboardButton button3 = new InlineKeyboardButton();

        button1.setText("Launch App");
        button1.setUrl("t.me/my_little_farmm_bot/myLittleFarm");
        button1.setCallbackData("APP_BUTTON");

        button2.setText("/help");
        button2.setCallbackData("HELP_BUTTON");

        button3.setText("/start");
        button3.setCallbackData("START_BUTTON");

        row1.add(button3);
        row1.add(button2);
        row2.add(button1);
        rows.add(row1);
        rows.add(row2);

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOT_USERNAME);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOT_TOKEN);
    }
}
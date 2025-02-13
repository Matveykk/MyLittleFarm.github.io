package botClient;

import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    // Метод, который вызывается при получении нового сообщения от пользователя
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
                //Ничего
            }
        }


        if (update.hasMessage() && update.getMessage().getWebAppData() != null) {
            String data = update.getMessage().getWebAppData().toString();
            long chatId = update.getMessage().getChatId();

            JSONObject json = new JSONObject(data);
            String message = json.getString("message");

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(message);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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

    //Метод для создания клавиатуры под сообщением
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

    // Метод, возвращающий имя бота
    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(BOT_USERNAME);
    }

    // Метод, возвращающий токен бота
    @Override
    public String getBotToken() {
        return PropertiesUtil.get(BOT_TOKEN); // Замените на токен вашего бота
    }
}

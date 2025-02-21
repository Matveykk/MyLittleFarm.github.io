package utils.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadBotProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadBotProperties() {
        try (InputStream inputStreamBot = PropertiesUtil.class.getClassLoader().getResourceAsStream("bot.properties");
                InputStream inputStreamDB = PropertiesUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            PROPERTIES.load(inputStreamBot);
            PROPERTIES.load(inputStreamDB);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

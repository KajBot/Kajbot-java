package support.kajstech.kajbot.utils;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static Properties config = new Properties();
    private static File cfgFile = new File("config.xml");

    private static void loadCfg() {
        try {
            config.loadFromXML(new FileInputStream(cfgFile));
        } catch (IOException e) {
            try {
                InputStream in = ClassLoader.getSystemResourceAsStream(cfgFile.getName());
                byte[] buffer = new byte[in.available()];
                OutputStream out = new FileOutputStream(cfgFile);
                in.read(buffer);
                out.write(buffer);
                config.load(new FileInputStream(cfgFile));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void storeCfg() {
        try {
            config.storeToXML(new FileOutputStream(cfgFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        loadCfg();
    }

    public static void shutdown() {
        storeCfg();

    }

    public static void setProperty(String key, String value) {
        config.setProperty(key, value);
        storeCfg();
    }

    public static String getProperty(String key) {
        if (config.isEmpty()) loadCfg();

        return config.getProperty(key);
    }

    public static boolean containsProperty(String key) {
        if (config.isEmpty()) loadCfg();
        try {
            if (getProperty(key).length() > 0 || !getProperty(key).isEmpty()) {
                return true;
            }
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }
}

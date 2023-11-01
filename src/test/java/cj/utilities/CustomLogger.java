package cj.utilities;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomLogger {
    private static Logger logger;

    static {
        try {
            // Initialize logger
            logger = Logger.getLogger("CustomLogger");
            FileHandler fileHandler = new FileHandler("test.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void logError(String message) {
        logger.log(Level.SEVERE, message);
    }
}

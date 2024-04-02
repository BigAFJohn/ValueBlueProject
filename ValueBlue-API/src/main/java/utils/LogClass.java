package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogClass {

    private static final Logger Log = LoggerFactory.getLogger("ValueBlue-Project");
    public static void startTestCase(String testCaseName) {
        Log.info("===============================================");
        Log.info("START: {}", testCaseName);
        Log.info("===============================================");
    }

    public static void endTestCase(String testCaseName) {
        Log.info("===============================================");
        Log.info("END: {}", testCaseName);
        Log.info("===============================================");
    }

    // Info Level Logs
    public static void info(String message, Object... args) {
        Log.info(message, args);
    }

    // Warn Level Logs
    public static void warn(String message, Object... args) {
        Log.warn(message, args);
    }

    // Error Level Logs
    public static void error(String message, Object... args) {
        Log.error(message, args);
    }

    // Fatal Level Logs
    public static void fatal(String message, Object... args) {
        Log.error(message, args);
    }

    // Debug Level Logs
    public static void debug(String message, Object... args)
    {
        Log.debug(message, args);
    }
}

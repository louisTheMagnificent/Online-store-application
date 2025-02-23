package com.project5348.email.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogUtil {

    // Create a logger instance
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    // Log an info message
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Log a debug message
    public static void logDebug(String message) {
        logger.debug(message);
    }

    // Log a warning message
    public static void logWarn(String message) {
        logger.warn(message);
    }

    // Log an error message
    public static void logError(String message) {
        logger.error(message);
    }

    // Log an error with exception
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}

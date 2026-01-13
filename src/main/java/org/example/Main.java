package org.example;

import org.example.ui.ConsoleUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ensureLogsDirectory();
        logger.info("Application starting...");
        
        try {
            ConsoleUI consoleUI = new ConsoleUI();
            consoleUI.start();
            logger.info("Application shutting down...");
        } catch (Exception e) {
            logger.error("Application error: ", e);
            throw e;
        }
    }

    private static void ensureLogsDirectory() {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
    }
}
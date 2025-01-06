package utils;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import configs.Config;

public class FileLogger {
	public static Logger getLogger(String name) {
		Logger logger;
		
		try {
			logger = Logger.getLogger(name);
			String logFilePath = Config.LOG_PATH + "/app.log";
			FileHandler fileHandler = new FileHandler(logFilePath, true);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return logger;
	}
}

package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import configs.Config;
import jakarta.servlet.http.HttpServletRequest;

public class LoggerManager {
    private static FileHandler accessFileHandler;
    private static FileHandler transactionFileHandler;
    private static FileHandler systemFileHandler;
    
    public static Logger accessLogger;
    public static Logger transactionLogger;
    public static Logger systemLogger;
    
    static {
        try {
            accessFileHandler = new FileHandler(Config.LOG_PATH + "access.log", true);
            accessFileHandler.setFormatter(new SimpleFormatter());
            accessLogger = Logger.getLogger("AccessLogger");
            accessLogger.addHandler(accessFileHandler);
            
            transactionFileHandler = new FileHandler(Config.LOG_PATH + "transaction.log", true);
            transactionFileHandler.setFormatter(new SimpleFormatter());
            transactionLogger = Logger.getLogger("TransactionLogger");
            transactionLogger.addHandler(transactionFileHandler);
            
            systemFileHandler = new FileHandler(Config.LOG_PATH + "system.log", true);
            systemFileHandler.setFormatter(new SimpleFormatter());
            systemLogger = Logger.getLogger("SystemLogger");
            systemLogger.addHandler(systemFileHandler);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LoggerManager() { }
    
	public static void logAccess(HttpServletRequest req, String urlPath, String method) {
		LoggerManager.accessLogger.log(Level.INFO, 
				"User: " + req.getSession().getAttribute("referenceNumber") + ", " +
		        "Accessed URL: " + urlPath + ", " +
				"HTTP Method: " + method + ", " +
				"IPaddress: " + req.getRemoteAddr() + ", " +
				"role: " + req.getSession().getAttribute("role"));
    }
	
	public static void logTransaction(HttpServletRequest req, String transaction) {
		LoggerManager.transactionLogger.log(Level.INFO,
				"User: " + req.getSession().getAttribute("referenceNumber") + ", " + 
				"Transaction: " + transaction + ", " +
				"IPaddress: " + req.getRemoteAddr() + ", " +
				"role: " + req.getSession().getAttribute("role"));
	}
}

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

import configs.Config;
import utils.LoggerManager;

public class DatabaseConnection {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		try {
		    Connection conn = DriverManager.getConnection(
		    		"jdbc:mysql://localhost/" + Config.SCHEMA +
		    		"?user=" + Config.USER + 
		    		"&password=" + Config.PASSWORD);
		    conn.setAutoCommit(true);
		    
		    return conn;
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return null;
	}
}

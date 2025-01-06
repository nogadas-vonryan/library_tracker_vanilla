package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import configs.Config;
import jakarta.servlet.http.HttpServlet;
import utils.FileLogger;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -8030989117293841257L;
	
	protected Connection conn;
	
	public BaseServlet() {
		Logger logger = FileLogger.getLogger(this.getClass().getName());
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
		
		try {
		    conn = DriverManager.getConnection(
		    		"jdbc:mysql://localhost/" + Config.SCHEMA +
		    		"?user=" + Config.USER + 
		    		"&password=" + Config.PASSWORD);
		    logger.info("Connected to the database");
		} catch (SQLException e) {
		    logger.severe("SQLException: " + e.getMessage());
		    logger.severe("SQLState: " + e.getSQLState());
		    logger.severe("VendorError: " + e.getErrorCode());
		}
	}
}

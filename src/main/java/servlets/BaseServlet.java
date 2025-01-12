package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import configs.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.FileLogger;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
	
	public Logger logger = FileLogger.getLogger(BaseServlet.class);
	public Connection conn;
	
	public BaseServlet() {
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
		} catch (SQLException e) {
		    logger.severe("SQLException: " + e.getMessage());
		    logger.severe("SQLState: " + e.getSQLState());
		    logger.severe("VendorError: " + e.getErrorCode());
		}
	}
	
	public void forward(HttpServletRequest req, HttpServletResponse resp, String name) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
	}
}

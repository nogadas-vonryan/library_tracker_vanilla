package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import configs.Config;
import database.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.LoggerManager;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
	
	public Connection conn;
	
	public BaseServlet() {
		conn = DatabaseConnection.getConnection();
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String name) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
	}
	
	protected void handleRedirect(HttpServletResponse resp, String url) {
		try {
			resp.sendRedirect(url);
		} catch (IOException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	protected void handleRollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}

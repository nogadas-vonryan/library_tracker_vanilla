package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import utils.FileLogger;

public class BookRepository {
	
	public Logger logger = FileLogger.getLogger(this.getClass().getName());
	
	public ResultSet findAllBooks(Connection conn) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book");
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet findById(Connection conn, int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet search(Connection conn, String searchTerm) {
        try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? OR author LIKE ? ");
			stmt.setString(1, "%" + searchTerm + "%");
			stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return null;
        }		
	}
}

package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import models.BorrowingRecord;
import utils.FileLogger;
import utils.ResultHandler;

public class BorrowingRecordRepository {

	public Logger logger = FileLogger.getLogger(this.getClass().getName());

	public List<BorrowingRecord> findAll(Connection conn) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record");
			ResultSet rs = stmt.executeQuery();
			List<BorrowingRecord> records = ResultHandler.getResultList(BorrowingRecord.class, rs);
			
			return records;
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}

	public ResultSet findById(Connection conn, int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE id = ?");
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
			PreparedStatement stmt = conn.prepareStatement("SELECT r.* "
					    		+ "FROM borrowing_record r "
					    		+ "JOIN user u ON r.user_id = u.id "
					    		+ "JOIN book b ON r.book_id = b.id "
					    		+ "WHERE u.first_name LIKE ? "
					    		+ "   OR u.last_name LIKE ? "
					    		+ "   OR u.reference_number LIKE ? "
					    		+ "   OR b.title LIKE ? "
					    		+ "   OR b.author LIKE ? ");
			stmt.setString(1, "%" + searchTerm + "%");
			stmt.setString(2, "%" + searchTerm + "%");
			stmt.setString(3, "%" + searchTerm + "%");
			stmt.setString(4, "%" + searchTerm + "%");
			stmt.setString(5, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet findByUserReferenceNumber(Connection conn, String userReferenceNumber) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record JOIN user ON borrowing_record.user_id = user.id WHERE reference_number = ?");
			stmt.setString(1, userReferenceNumber);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet findRecordsByMonthAndYear(Connection conn, int month, int year) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE MONTH(borrow_date) = ? AND YEAR(borrow_date) = ?");
			stmt.setInt(1, month);
			stmt.setInt(2, year);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet findRecordsIfReturned(Connection conn, boolean isReturned) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE is_returned = ?");
			stmt.setBoolean(1, isReturned);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
	
	public ResultSet getRecordAnalytics(Connection conn, int year) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT DATE_FORMAT(MIN(borrow_date), '%M') AS month, COUNT(*) AS number FROM borrowing_record WHERE YEAR(borrow_date) = ? GROUP BY MONTH(borrow_date) ORDER BY MONTH(borrow_date)");
			stmt.setInt(1, year);
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			return null;
		}
	}
}

package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import models.BorrowingRecord;
import models.MonthlyRecord;
import utils.FileLogger;
import utils.ResultHandler;

public class BorrowingRecordRepository {

	public Logger logger = FileLogger.getLogger(this.getClass().getName());

	public void insert(Connection conn, BorrowingRecord record) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO borrowing_record (user_id, book_id, borrow_date, return_date, is_returned) VALUES (?, ?, ?, ?, ?)");
		stmt.setInt(1, record.getUser().getId());
		stmt.setInt(2, record.getBook().getId());
		stmt.setString(3, record.getBorrowDate());
		stmt.setString(4, record.getReturnDate());
		stmt.setBoolean(5, record.isReturned());
		stmt.executeUpdate();
	}	
	
	public void update(Connection conn, BorrowingRecord record) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"UPDATE borrowing_record SET user_id = ?, book_id = ?, borrow_date = ?, return_date = ?, is_returned = ? WHERE id = ?");
		stmt.setInt(1, record.getUser().getId());
		stmt.setInt(2, record.getBook().getId());
		stmt.setString(3, record.getBorrowDate());
		stmt.setString(4, record.getReturnDate());
		stmt.setBoolean(5, record.isReturned());
		stmt.setInt(6, record.getId());
		stmt.executeUpdate();
	}
	
	public void delete(Connection conn, int id) throws SQLException {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM borrowing_record WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
	}
	
	public List<BorrowingRecord> findAll(Connection conn) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM borrowing_record JOIN user ON borrowing_record.user_id = user.id JOIN book ON borrowing_record.book_id = book.id");
		ResultSet rs = stmt.executeQuery();
		List<BorrowingRecord> records = ResultHandler.getResultList(conn, BorrowingRecord.class, rs);
		
		return records;
	}

	public BorrowingRecord findById(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		BorrowingRecord record = ResultHandler.getResult(conn, BorrowingRecord.class, rs);
		
		return record;
	}

	public List<BorrowingRecord> search(Connection conn, String searchTerm) throws SQLException {
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
        List<BorrowingRecord> records = ResultHandler.getResultList(conn, BorrowingRecord.class, rs);
		return records;
	}
	
	public List<BorrowingRecord> findByUserReferenceNumber(Connection conn, String userReferenceNumber) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record JOIN user ON borrowing_record.user_id = user.id WHERE reference_number = ?");
		stmt.setString(1, userReferenceNumber);
		ResultSet rs = stmt.executeQuery();
		List<BorrowingRecord> records = ResultHandler.getResultList(conn, BorrowingRecord.class, rs);
		return records;
	}
	
	public List<BorrowingRecord> findRecordsByMonthAndYear(Connection conn, int month, int year) throws SQLException {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE MONTH(borrow_date) = ? AND YEAR(borrow_date) = ?");
			stmt.setInt(1, month);
			stmt.setInt(2, year);
			ResultSet rs = stmt.executeQuery();
			List<BorrowingRecord> records = ResultHandler.getResultList(conn, BorrowingRecord.class, rs);
			return records;
	}
	
	public List<BorrowingRecord> findRecordsIfReturned(Connection conn, boolean isReturned) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrowing_record WHERE is_returned = ?");
		stmt.setBoolean(1, isReturned);
		ResultSet rs = stmt.executeQuery();
		List<BorrowingRecord> records = ResultHandler.getResultList(conn, BorrowingRecord.class, rs);
		return records;
	}
	
	public List<MonthlyRecord> getRecordAnalytics(Connection conn, Integer year) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT DATE_FORMAT(MIN(borrow_date), '%M') AS month, COUNT(*) AS number FROM borrowing_record WHERE YEAR(borrow_date) = ? GROUP BY MONTH(borrow_date) ORDER BY MONTH(borrow_date)");
		stmt.setInt(1, year);
		ResultSet rs = stmt.executeQuery();
		List<MonthlyRecord> records = ResultHandler.getResultList(conn, MonthlyRecord.class, rs);
		return records;
	}
}

package models;

import java.sql.ResultSet;
import java.util.logging.Logger;

import lombok.Getter;

@Getter
public class BorrowingRecord {
	public int id;
	public int userId;
	public int bookId;
	public String dateBorrowed;
	public String dateReturned;
	public String status;
	
	public BorrowingRecord(int id, int userId, int bookId, String dateBorrowed, String dateReturned, String status) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.dateBorrowed = dateBorrowed;
		this.dateReturned = dateReturned;
		this.status = status;
	}
	
	public BorrowingRecord(ResultSet rs) {
		Logger logger = Logger.getLogger(this.getClass().getName());
		
		try {
            this.id = rs.getInt("id");
            this.userId = rs.getInt("user_id");
            this.bookId = rs.getInt("book_id");
            this.dateBorrowed = rs.getString("borrow_date");
            this.dateReturned = rs.getString("return_date");
            this.status = rs.getString("is_returned");
            
        } catch (Exception e) {
        	logger.severe(e.getMessage());
        }
	}
}

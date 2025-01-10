package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.Setter;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import repository.UserRepository;

@Getter
@Setter
public class BorrowingRecord {
	public int id;
	public User user;
	public Book book;
	public String borrowDate;
	public String returnDate;
	public boolean isReturned;
	
	public BorrowingRecord(User user, Book book, String dateBorrowed, String dateReturned) {
		this.user = user;
		this.book = book;
		this.borrowDate = dateBorrowed;
		this.returnDate = dateReturned;
		this.isReturned = false;
	}
	
	public BorrowingRecord(Connection conn, ResultSet rs) {
		Logger logger = Logger.getLogger(this.getClass().getName());
		UserRepository userRepository = new UserRepository();
		BookRepository bookRepository = new BookRepository();
		
		try {
            this.id = rs.getInt("id");
            this.user = userRepository.findById(conn, rs.getInt("user_id"));
            this.book = bookRepository.findById(conn, rs.getInt("book_id"));
            this.borrowDate = rs.getString("borrow_date");
            this.returnDate = rs.getString("return_date");
            this.isReturned = rs.getBoolean("is_returned");
            
        } catch (Exception e) {
        	logger.severe(e.getMessage());
        }
	}
	
	public void save(Connection conn) {
		BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();

		if (this.id == 0) {
			borrowingRecordRepository.insert(conn, this);
		} else {
			borrowingRecordRepository.update(conn, this);
		}
	}
}

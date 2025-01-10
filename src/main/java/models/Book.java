package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import repository.BookRepository;

@Getter
@Setter
public class Book {
	public int id;
	public String author;
	public String title;
	public String category;
	public String dateCreated;
	public String imageUrl;
	
	public Book() { }
	
	public Book(String author, String title, String category, String dateCreated, String imageUrl) {
		this.author = author;
		this.title = title;
		this.category = category;
		this.dateCreated = dateCreated;
		this.imageUrl = imageUrl;
	}
	
	public Book(Connection conn, ResultSet rs) throws SQLException {		
		this.id = rs.getInt("id");
		this.author = rs.getString("author");
		this.title = rs.getString("title");
		this.category = rs.getString("category");
		this.dateCreated = rs.getString("date_created");
		this.imageUrl = rs.getString("image_url");
	}	
	
	public void save(Connection conn) throws SQLException {
		BookRepository bookRepository = new BookRepository();
		
		if (this.id == 0) {
			bookRepository.insert(conn, this);
		} else {
			bookRepository.update(conn, this);
		}
	}
}

package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Book;
import utils.ResultHandler;

public class BookRepository {

	public void insert(Connection conn, Book book) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO book (author, title, category, date_created, image_url) VALUES (?, ?, ?, ?, ?)");
		stmt.setString(1, book.author);
		stmt.setString(2, book.title);
		stmt.setString(3, book.category);
		stmt.setString(4, book.dateCreated);
		stmt.setString(5, book.imageUrl);
		stmt.executeUpdate();
	}

	public void update(Connection conn, Book book) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(
				"UPDATE book SET author = ?, title = ?, category = ?, date_created = ?, image_url = ? WHERE id = ?");
		stmt.setString(1, book.author);
		stmt.setString(2, book.title);
		stmt.setString(3, book.category);
		stmt.setString(4, book.dateCreated);
		stmt.setString(5, book.imageUrl);
		stmt.setInt(6, book.id);
		stmt.executeUpdate();
	}
	
	public void delete(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE id = ?");
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}
	
	public Book findByTitle(Connection conn, String title) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE title = ?");
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		Book book = ResultHandler.getResult(conn, Book.class, rs);
		
		return book;
	}

	public List<Book> findAll(Connection conn) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book");
		ResultSet rs = stmt.executeQuery();
		List<Book> books = ResultHandler.getResultList(conn, Book.class, rs);

		return books;
	}

	public Book findById(Connection conn, int id) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Book book = ResultHandler.getResult(conn, Book.class, rs);
		return book;
	}

	public List<Book> search(Connection conn, String searchTerm) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE title LIKE ? OR author LIKE ? ");
		stmt.setString(1, "%" + searchTerm + "%");
		stmt.setString(2, "%" + searchTerm + "%");
		ResultSet rs = stmt.executeQuery();
		List<Book> books = ResultHandler.getResultList(conn, Book.class, rs);

		return books;
	}
}

package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import services.Auth;
import utils.FileUploader;
import utils.LoggerManager;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/admin/books/add")
public class AdminBookAddServlet extends BaseServlet {

	private static final long serialVersionUID = 8L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoggerManager.logAccess(req, "/admin/books/add", "GET");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}

		forward(req, resp, "admin-books-add");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/books/add", "POST");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		String fileName = null;
		
		try {
			fileName = FileUploader.save(req.getPart("bookCover"));
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}

		Book book = new Book(req.getParameter("author"), 
							 req.getParameter("title"), 
							 req.getParameter("category"),
							 LocalDate.now().toString(), 
							 fileName);
		
		try {
			book.save(conn);
			conn.commit();
			LoggerManager.logTransaction(req, "Add Book ID");
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}

		try {
			resp.sendRedirect("/admin/books");
		} catch (IOException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
}

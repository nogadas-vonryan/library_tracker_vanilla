package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import services.Auth;
import utils.FileUploader;

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
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}

		forward(req, resp, "admin-books-add");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = FileUploader.save(req.getPart("bookCover"));

		Book book = new Book(req.getParameter("author"), 
							 req.getParameter("title"), 
							 req.getParameter("category"),
							 req.getParameter("dateCreated"), 
							 fileName);
		
		try {
			book.save(conn);
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		resp.sendRedirect("/admin/books");
	}
	
}

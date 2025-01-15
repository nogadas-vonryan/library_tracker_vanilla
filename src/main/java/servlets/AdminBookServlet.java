package servlets;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import repository.BookRepository;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/admin/books")
public class AdminBookServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	BookRepository bookRepository = new BookRepository();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {	
		LoggerManager.logAccess(req, "/admin/books", "GET");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
			return;
		}
		
		String search = req.getParameter("search");
		List<Book> books = null;
		
		if (search == null) search = "";
		
		try {
			books = bookRepository.search(conn, search);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		req.setAttribute("books", books);
		
		try {
			forward(req, resp, "admin-books"); 
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}

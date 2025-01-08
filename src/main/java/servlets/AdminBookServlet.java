package servlets;

import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import repository.BookRepository;
import services.Auth;

@WebServlet("/admin/books")
public class AdminBookServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	BookRepository bookRepository = new BookRepository();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {	
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/library_tracker/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			List<Book> books = bookRepository.findAll(conn);
			req.setAttribute("books", books);
			forward(req, resp, "admin-books");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		} 
	}
}

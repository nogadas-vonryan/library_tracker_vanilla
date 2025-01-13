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
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			String search = req.getParameter("search");
			
			if (search == null) search = "";
			
			List<Book> books = bookRepository.search(conn, search);
			req.setAttribute("books", books);
			forward(req, resp, "admin-books");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		} 
	}
}

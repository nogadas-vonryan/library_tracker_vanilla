package servlets;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import repository.BookRepository;
import utils.LoggerManager;

@WebServlet("/user/books/*")
public class UserBookDetailServlet extends BaseServlet {

	private static final long serialVersionUID = 15L;
	BookRepository bookRepository = new BookRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		 String path = req.getPathInfo();
	        if (path == null) {
	            try {
					resp.sendRedirect("user/books");
				} catch (IOException e) {
					LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
				}
	            return;
	        }

	        String[] parts = path.split("/");
	        String id = parts[1];
		
		try {
			Book book = bookRepository.findById(conn, Integer.parseInt(id));
			req.setAttribute("book", book);
			forward(req, resp, "user-books-read");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
}

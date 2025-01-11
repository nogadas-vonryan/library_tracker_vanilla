package servlets;

import java.util.List;
import java.util.logging.FileHandler;

import configs.Config;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import repository.BookRepository;
import services.Auth;
import utils.FileUploader;

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
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		String method = req.getParameter("_method");

		try {
			switch (method) {
				case "POST": {
					String fileName = FileUploader.save(req.getPart("imageUrl"));
					
					Book book = new Book(req.getParameter("author"), 
										 req.getParameter("title"), 
										 req.getParameter("category"),
										 req.getParameter("dateCreated"), 
										 fileName);
					book.save(conn);
					break;
				}
	
				case "PUT": {
					Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("bookId")));
					book.setTitle(req.getParameter("title"));
					book.setAuthor(req.getParameter("author"));
					book.save(conn);
					resp.sendRedirect("/admin/books?success=BookUpdated");
					break;
				}
	
				case "DELETE": {
					int id = Integer.parseInt(req.getParameter("id"));
					bookRepository.delete(conn, id);
					resp.sendRedirect("/admin/books?success=BookDeleted");
					break;
				}
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
}

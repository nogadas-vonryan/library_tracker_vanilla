package servlets;

import java.nio.file.Files;
import java.nio.file.Paths;

import configs.Config;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Book;
import repository.BookRepository;
import services.Auth;
import utils.FileUploader;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/admin/books/*")
public class AdminBookUpdateServlet extends BaseServlet {

	private static final long serialVersionUID = 9L;
	BookRepository bookRepository = new BookRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		String path = req.getPathInfo();
		if (path == null) {
			try {
				resp.sendRedirect("/admin/books");
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
			return;
		}

		String[] parts = path.split("/");
		String id = parts[1];
		Book book = new Book();
		
		try {
			book = bookRepository.findById(conn, Integer.parseInt(id));
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
		
		req.setAttribute("book", book);		
		
		try {
			forward(req, resp, "admin-books-edit");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String method = (String) req.getParameter("_method");
		
		if(method == null) {
            try {
                resp.sendRedirect("/admin/books");
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
            return;
		}
		
		switch(method) {
            case "PUT":
            	try {
            		put(req, resp);
            	} catch (Exception e) {
            		logger.severe(e.getMessage());
            	}
                break;
            case "DELETE":
            	try {
            		delete(req, resp);
				} catch (Exception e) {
					logger.severe(e.getMessage());
				}
				break;
            default:
            	try {
            		resp.sendRedirect("/admin/books");
            	} catch (Exception e) {
            		logger.severe(e.getMessage());
				}
                break;
		}
	}	
	
	private void put(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Book book = new Book();
		Part filePart = req.getPart("bookCover");
		String fileName = "";

		book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
		
		if (filePart != null  && filePart.getSize() > 0) {
			fileName = FileUploader.save(filePart);
		} else {
			fileName = "";	
		}
		
		book.setTitle(req.getParameter("title").isEmpty() ? book.getTitle() : req.getParameter("title"));
		book.setAuthor(req.getParameter("author").isEmpty() ? book.getAuthor() : req.getParameter("author"));
		book.setCategory(req.getParameter("category").isEmpty() ? book.getCategory() : req.getParameter("category"));
		book.setImageUrl(fileName.isEmpty() ? book.getImageUrl() : fileName);
		book.save(conn);

		resp.sendRedirect("/admin/books");
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
		
		Files.delete(Paths.get(Config.UPLOAD_PATH + book.getImageUrl()));
		bookRepository.delete(conn, book.getId());
		
		resp.sendRedirect("/admin/books");
	}
}
package servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

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
import utils.LoggerManager;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/admin/books/*")
public class AdminBookUpdateServlet extends BaseServlet {

    private static final long serialVersionUID = 9L;
    private final BookRepository bookRepository = new BookRepository();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    	LoggerManager.logAccess(req, "/admin/books/*", "GET");
    	
        if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
        	handleRedirect(resp, "/login");
        	return;
        }
        
        String path = req.getPathInfo();
        if (path == null) {
        	LoggerManager.systemLogger.log(Level.WARNING, "Path is null");
        	handleRedirect(resp, "/admin/books");
        	return;
        }

        String[] parts = path.split("/");
        String id = parts[1];

        try {
            Book book = bookRepository.findById(conn, Integer.parseInt(id));
            req.setAttribute("book", book);        
            forward(req, resp, "admin-books-edit");
        } catch (Exception e) {
        	handleRedirect(resp, "/admin/books?error=BookNotFound");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
            handleRedirect(resp, "/login");
            return;
        }
        
        String method = req.getParameter("_method");
        
        if (method == null) {
        	LoggerManager.systemLogger.log(Level.WARNING, "Method is null");
            handleRedirect(resp, "/admin/books");
            return;
        }
        
        switch (method) {
            case "PUT":
            	LoggerManager.logAccess(req, "/admin/books/*", "PUT");
                handlePut(req, resp);
                break;
            case "DELETE":
            	LoggerManager.logAccess(req, "/admin/books/*", "DELETE");
                handleDelete(req, resp);
                break;
            default:
            	LoggerManager.systemLogger.log(Level.WARNING, "Invalid Method");
                handleRedirect(resp, "/admin/books");
                break;
        }
    }

    private void handlePut(HttpServletRequest req, HttpServletResponse resp) {
        try {
        	Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
            Part filePart = req.getPart("bookCover");
            String fileName = (filePart != null && filePart.getSize() > 0) ? FileUploader.save(filePart) : "";

            book.setTitle(getParameterOrDefault(req, "title", book.getTitle()));
            book.setAuthor(getParameterOrDefault(req, "author", book.getAuthor()));
            book.setCategory(getParameterOrDefault(req, "category", book.getCategory()));
            book.setImageUrl(fileName.isEmpty() ? book.getImageUrl() : fileName);
            book.save(conn);
            
            LoggerManager.logTransaction(req, "Update Book ID: " + book.getId());

            handleRedirect(resp, "/admin/books");
        } catch (Exception e) {
        	LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
        	handleRollback();
            handleRedirect(resp, "/admin/books?error=CannotUpdateBook");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
        	Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
            String imageUrl = book.getImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Files.delete(Paths.get(Config.UPLOAD_PATH + imageUrl));
            }
            
            bookRepository.delete(conn, book.getId());
            LoggerManager.logTransaction(req, "Delete Book ID: " + book.getId());
            
            resp.sendRedirect("/admin/books");
        } catch (Exception e) {
        	LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage());
        	handleRollback();
            handleRedirect(resp, "/admin/books?error=CannotDeleteBook");
        }
    }

    private String getParameterOrDefault(HttpServletRequest req, String paramName, String defaultValue) {
        String paramValue = req.getParameter(paramName);
        return (paramValue == null || paramValue.isEmpty()) ? defaultValue : paramValue;
    }
}

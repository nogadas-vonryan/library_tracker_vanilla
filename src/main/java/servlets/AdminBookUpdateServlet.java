package servlets;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

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
    private final BookRepository bookRepository = new BookRepository();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
            redirectToLogin(resp);
            return;
        }
        
        String path = req.getPathInfo();
        if (path == null) {
            redirectToBooksPage(resp);
            return;
        }

        String[] parts = path.split("/");
        String id = parts[1];

        try {
            Book book = bookRepository.findById(conn, Integer.parseInt(id));
            req.setAttribute("book", book);        
            forward(req, resp, "admin-books-edit");
        } catch (Exception e) {
            logAndRedirectError(e, resp, "admin/books");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
            redirectToLogin(resp);
            return;
        }
        
        String method = req.getParameter("_method");
        
        if (method == null) {
            redirectToBooksPage(resp);
            return;
        }
        
        switch (method) {
            case "PUT":
                handlePut(req, resp);
                break;
            case "DELETE":
                handleDelete(req, resp);
                break;
            default:
                redirectToBooksPage(resp);
                break;
        }
    }

    private void handlePut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            put(req, resp);
        } catch (Exception e) {
            logAndRedirectError(e, resp, "/admin/books?error=CannotUpdateBook");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            delete(req, resp);
        } catch (Exception e) {
            logAndRedirectError(e, resp, "/admin/books?error=CannotDeleteBook");
        }
    }

    private void put(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
        Part filePart = req.getPart("bookCover");
        String fileName = (filePart != null && filePart.getSize() > 0) ? FileUploader.save(filePart) : "";

        book.setTitle(getParameterOrDefault(req, "title", book.getTitle()));
        book.setAuthor(getParameterOrDefault(req, "author", book.getAuthor()));
        book.setCategory(getParameterOrDefault(req, "category", book.getCategory()));
        book.setImageUrl(fileName.isEmpty() ? book.getImageUrl() : fileName);
        book.save(conn);

        resp.sendRedirect("/admin/books");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        Book book = bookRepository.findById(conn, Integer.parseInt(req.getParameter("id")));
        String imageUrl = book.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Files.delete(Paths.get(Config.UPLOAD_PATH + imageUrl));
        }
        
        bookRepository.delete(conn, book.getId());
        resp.sendRedirect("/admin/books");
    }

    private String getParameterOrDefault(HttpServletRequest req, String paramName, String defaultValue) {
        String paramValue = req.getParameter(paramName);
        return (paramValue == null || paramValue.isEmpty()) ? defaultValue : paramValue;
    }

    private void redirectToLogin(HttpServletResponse resp) {
        try {
            resp.sendRedirect("/login");
        } catch (IOException e) {
            logger.severe("Error redirecting to login: " + e.getMessage());
        }
    }

    private void redirectToBooksPage(HttpServletResponse resp) {
        try {
            resp.sendRedirect("/admin/books");
        } catch (IOException e) {
            logger.severe("Error redirecting to books page: " + e.getMessage());
        }
    }

    private void logAndRedirectError(Exception e, HttpServletResponse resp, String redirectUrl) {
        logger.severe("Error: " + e.getMessage());
        try {
            resp.sendRedirect(redirectUrl);
        } catch (IOException ioException) {
            logger.severe("Error redirecting: " + ioException.getMessage());
        }
    }
}

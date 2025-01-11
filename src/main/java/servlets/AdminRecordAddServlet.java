package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Book;
import models.BorrowingRecord;
import models.User;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import repository.UserRepository;
import services.Auth;

@WebServlet("/admin/records/add")
public class AdminRecordAddServlet extends BaseServlet {
	
	private static final long serialVersionUID = 10L;
	UserRepository userRepository = new UserRepository();
	BookRepository bookRepository = new BookRepository();
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			forward(req, resp, "admin-records-add");
		} catch (Exception e) {
            logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			User user = userRepository.findByReferenceNumber(conn, req.getParameter("studentNumber"));
			Book book = bookRepository.findByTitle(conn, req.getParameter("bookTitle"));
			
			BorrowingRecord record = new BorrowingRecord(user, book, LocalDate.now().toString(), req.getParameter("returnDate"));
			record.save(conn);
			
			resp.sendRedirect("/admin/records");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			
			try {
				resp.sendRedirect("/admin/records/add?error=RecordNotAdded");
			} catch (IOException e1) {
				logger.severe(e1.getMessage());
			}
		}
	}
	
}

package servlets;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

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
import services.RecordService;
import utils.LoggerManager;

@WebServlet("/admin/records/add")
public class AdminRecordAddServlet extends BaseServlet {
	
	private static final long serialVersionUID = 10L;
	UserRepository userRepository = new UserRepository();
	BookRepository bookRepository = new BookRepository();
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/records/add", "GET");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
			return;
		}
		
		try {
			List<Book> books = bookRepository.findAll(conn);
			List<User> users = userRepository.findAll(conn);
			
			req.setAttribute("books", books);
			req.setAttribute("users", users);
			req.setAttribute("error", req.getParameter("error"));
			
			forward(req, resp, "admin-records-add");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/admin/records/add", "POST");
		
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			handleRedirect(resp, "/login");
			return;
		}
		
		User user = null;
		Book book = null;
		
		try {
			user = userRepository.findByReferenceNumber(conn, req.getParameter("studentNumber"));
			book = bookRepository.findByTitle(conn, req.getParameter("bookTitle"));
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.WARNING, e.getMessage(), e);
			handleRedirect(resp, "/admin/records/add?error=InvalidUserOrBook");
			return;
		}
		
		String returnDate = req.getParameter("returnDate");

		if(RecordService.isExpired(returnDate)) {
			LoggerManager.systemLogger.log(Level.WARNING, "Return date is expired");
            handleRedirect(resp, "/admin/records/add?error=ExpiredReturnDate");
            return;
		}
		
		BorrowingRecord record = new BorrowingRecord(user, book, LocalDate.now().toString(), returnDate);
		
		try {
			record.save(conn);
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.WARNING, e.getMessage());
			handleRollback();
			handleRedirect(resp, "/admin/records/add?error=CannotAddRecord");
			return;
		}
		
		LoggerManager.logTransaction(req, "Add Record ID");
		handleRedirect(resp, "/admin/records");
	}
}

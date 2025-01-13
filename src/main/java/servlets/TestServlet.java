package servlets;

import java.util.List;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import repository.PasswordRequestRepository;
import repository.UserRepository;
import utils.LoggerManager;

@WebServlet("/tests")
public class TestServlet extends BaseServlet {
	private static final long serialVersionUID = 5L;
	
	private UserRepository userRepository = new UserRepository();
	private BookRepository bookRepository = new BookRepository();
	private BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	private PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			int x = 10/0;
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}

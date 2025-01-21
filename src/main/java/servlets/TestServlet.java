package servlets;

import java.io.Console;
import java.sql.SQLException;
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
import services.RecordService;
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
			List<BorrowingRecord> records = borrowingRecordRepository.findAll(conn);
			BorrowingRecord record = records.getFirst();
			System.out.println(RecordService.daysLeftBeforeExpiry(record));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

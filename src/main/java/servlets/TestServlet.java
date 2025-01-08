package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import repository.UserRepository;

@WebServlet("/test")
public class TestServlet extends BaseServlet {
	private static final long serialVersionUID = 5L;
	
	private UserRepository userRepository = new UserRepository();
	private BookRepository bookRepository = new BookRepository();
	private BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("TestServlet's doGet method is called");
		
		try {
			BorrowingRecord record = borrowingRecordRepository.findById(conn, 4);
			System.out.println(record.user.firstName + " " + record.user.lastName);
			logger.info("User found");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

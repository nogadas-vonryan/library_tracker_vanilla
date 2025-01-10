package servlets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import models.PasswordRequest;
import models.User;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import repository.PasswordRequestRepository;
import repository.UserRepository;

@WebServlet("/tests")
public class TestServlet extends BaseServlet {
	private static final long serialVersionUID = 5L;
	
	private UserRepository userRepository = new UserRepository();
	private BookRepository bookRepository = new BookRepository();
	private BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	private PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("TestServlet's doGet method is called");
		
		try {
			PasswordRequest request = passwordRequestRepository.findByUserId(conn, 2);
			System.out.println(request.getUser().getFirstName() + request.getUser().getLastName() + request.getDateTimeCreated());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

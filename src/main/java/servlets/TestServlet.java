package servlets;

import java.sql.ResultSet;
import java.util.logging.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.BookRepository;
import repository.BorrowingRecordRepository;
import utils.FileLogger;

@WebServlet("/test")
public class TestServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = FileLogger.getLogger(this.getClass().getName());
	private BookRepository bookRepository = new BookRepository();
	private BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	public TestServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		ResultSet rs = borrowingRecordRepository.findByUserReferenceNumber(conn, "2022-00001-TG-0");
		
		try {			
			while (rs.next()) {
				System.out.println(rs.getString("first_name"));
			}
			
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
}

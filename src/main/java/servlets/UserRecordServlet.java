package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BorrowingRecordRepository;
import services.Auth;

@WebServlet("/user/records")
public class UserRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 7L;
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isUser(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			String search = req.getParameter("search");
			String status = req.getParameter("status");
			String month = req.getParameter("month");
			String year = req.getParameter("year");
			String sortOrder = req.getParameter("sortOrder");
			
			String referenceNumber = req.getSession().getAttribute("referenceNumber").toString();
			List<BorrowingRecord> records = borrowingRecordRepository.findByUserReferenceNumber(conn, referenceNumber);
			
			records = records.stream()
					.filter(record -> search == null || record.getBook().getTitle().toLowerCase().contains(search.toLowerCase()))
					.filter(record -> status == null || (status.equals("returned") && record.isReturned()) || (status.equals("borrowing") && !record.isReturned()))
					.filter(record -> month == null || LocalDate.parse(record.getBorrowDate()).getMonthValue() == Integer.parseInt(month))
					.filter(record -> year == null || LocalDate.parse(record.getBorrowDate()).getYear() == Integer.parseInt(year))
					.sorted((record1, record2) -> {
				        LocalDate date1 = LocalDate.parse(record1.getBorrowDate());
				        LocalDate date2 = LocalDate.parse(record2.getBorrowDate());
				        
				        if (sortOrder != null && sortOrder.equals("asc")) {
				            return date1.compareTo(date2);
				        } else {
				            return date2.compareTo(date1);
				        }
				    })
					.toList();
			
			req.setAttribute("records", records);
			forward(req, resp, "user-records");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
}

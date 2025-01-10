package servlets;

import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BorrowingRecordRepository;
import repository.UserRepository;
import services.Auth;

@WebServlet("/admin/records")
public class AdminRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 2L;
	
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/library_tracker/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			List<BorrowingRecord> records = borrowingRecordRepository.findAll(conn);
			req.setAttribute("records", records);
			forward(req, resp, "admin-records");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String method = req.getParameter("_method");
		
		try {
			switch(method) {
				case "POST":
					break;
					
				case "PUT":
					BorrowingRecord record = borrowingRecordRepository.findById(conn, Integer.parseInt(req.getParameter("recordId")));
					record.setReturned(req.getParameter("isReturned").equals("true") ? true : false);
					record.save(conn);
					resp.sendRedirect("/library_tracker/admin/records?success=RecordUpdated");
					break;
			
			    case "DELETE":
	                int id = Integer.parseInt(req.getParameter("id"));
	                borrowingRecordRepository.delete(conn, id);
	                resp.sendRedirect("/library_tracker/admin/records?success=RecordDeleted");
	                break;
	            default:
	            	resp.sendRedirect("/library_tracker/admin/records?error=InvalidMethod");
	                break;
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	
}

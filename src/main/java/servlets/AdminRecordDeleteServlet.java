package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.BorrowingRecord;
import repository.BorrowingRecordRepository;
import services.Auth;

@WebServlet("/admin/records/delete")
public class AdminRecordDeleteServlet extends BaseServlet {
	
	private static final long serialVersionUID = 13L;
	
	BorrowingRecordRepository borrowingRecordRepository = new BorrowingRecordRepository();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req) || !Auth.isAdmin(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
            int id = Integer.parseInt(req.getParameter("id"));
            borrowingRecordRepository.delete(conn, id);
            resp.sendRedirect("/admin/records?success=RecordDeleted");
            
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
}

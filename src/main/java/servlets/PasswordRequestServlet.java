package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PasswordRequest;
import models.User;
import repository.PasswordRequestRepository;
import repository.UserRepository;
import services.Auth;

@WebServlet("/admin/password-requests")
public class PasswordRequestServlet extends BaseServlet {

	private static final long serialVersionUID = 11L;
	
	UserRepository userRepository = new UserRepository();
	PasswordRequestRepository passwordRequestRepository = new PasswordRequestRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (!Auth.isLoggedIn(req)) {
			try {
				resp.sendRedirect("/login");
				return;
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
		
		try {
			List<PasswordRequest> requests = passwordRequestRepository.findAll(conn);
			
			for (PasswordRequest request : requests) {
				System.out.println(request.getUser().getFirstName() + request.getUser().getLastName() + request.getDateTimeCreated());
			}
			
			req.setAttribute("requests", requests);
			forward(req, resp, "admin-password-requests");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		} catch (ServletException e) {
			logger.severe(e.getMessage());
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {		
		String method = req.getParameter("_method");
		
		try {
			if (Auth.isLoggedIn(req) && method.equals("DELETE")) {
				delete(req, resp);
				resp.sendRedirect("/admin/password-requests?success=RequestDeleted");
			} else if(method.equals("POST")) {
				insert(req, resp);
				resp.sendRedirect("/admin/password-requests?success=RequestCreated");
			} else {
				resp.sendRedirect("/login");
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	private void insert(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		User user;
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
			PasswordRequest request = new PasswordRequest(user);
			request.save(conn);
			
			resp.sendRedirect("/login?success=PasswordRequestSent");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		try {
			passwordRequestRepository.delete(conn, Integer.parseInt(id));
			resp.sendRedirect("/admin/password-requests?success=RequestDeleted");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
}

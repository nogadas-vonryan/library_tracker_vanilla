package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import repository.UserRepository;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
	
	private static final long serialVersionUID = 14L;
	UserRepository userRepository = new UserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "register");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		
		User user;
		
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
		} catch (SQLException e) {
			user = null;
		}
		
		if (user != null) {
			try {
				resp.sendRedirect("register?error=ReferenceNumberExists");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			try {
				resp.sendRedirect("register?error=PasswordMismatch");
			} catch (IOException e) {
				logger.severe(e.getMessage());
			}
			return;
		}
		
		user = new User(referenceNumber, firstName, lastName, password);
		user.setRole("USER");
		
		try {
			user.save(conn);
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		try {
			resp.sendRedirect("login");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}
	
	
}

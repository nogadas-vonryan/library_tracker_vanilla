package servlets;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.regex.Pattern;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import repository.UserRepository;
import services.Auth;
import utils.LoggerManager;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
	
	private static final long serialVersionUID = 14L;
	UserRepository userRepository = new UserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		if (Auth.isLoggedIn(req)) {
			if (Auth.isAdmin(req)) {
				handleRedirect(resp, "admin/books");
			} else {
				handleRedirect(resp, "user/books");
			}
			return;
		}
		
		try {
			forward(req, resp, "register");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String referenceNumber = req.getParameter("referenceNumber");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		
		Pattern referenceNumberPattern = Pattern.compile("\\d{4}-\\d{5}-[A-Z]{2}-\\d");
		Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[A-Za-z\\W\\d]{8,}$");
		
		if (!referenceNumberPattern.matcher(referenceNumber).matches()) {
			LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to register: Invalid Reference Number");
			handleRedirect(resp, "register?error=InvalidReferenceNumber");
			return;
		}
		
		if (!passwordPattern.matcher(password).matches()) {
			LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to register: Invalid Password");
			handleRedirect(resp, "register?error=InvalidPassword");
			return;
		}
		
		User user;
		
		try {
			user = userRepository.findByReferenceNumber(conn, referenceNumber);
		} catch (SQLException e) {
			user = null;
		}
		
		if (user != null) {
			handleRedirect(resp, "register?error=ReferenceNumberExists");
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			handleRedirect(resp, "register?error=PasswordMismatch");
			return;
		}
		
		user = new User(referenceNumber, firstName, lastName, password);
		user.setRole("USER");
		
		try {
			user.save(conn);
			LoggerManager.logTransaction(req, "User Registered: " + user.getId());
		} catch (SQLException e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		handleRedirect(resp, "login?success=SuccessfullyRegistered");
	}
}

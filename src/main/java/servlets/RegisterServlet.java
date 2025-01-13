package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
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
			try {
				if (Auth.isAdmin(req)) {
					resp.sendRedirect("admin/books");
				} else {
					resp.sendRedirect("user/books");
				}
				return;
			} catch (Exception e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
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
			try {
				LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to register: Invalid Reference Number");
				resp.sendRedirect("register?error=InvalidReferenceNumber");
			} catch (IOException e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
			return;
		}
		
		if (!passwordPattern.matcher(password).matches()) {
			try {
				LoggerManager.accessLogger.info("User: " + req.getRemoteAddr() + " Failed to register: Invalid Password");
				resp.sendRedirect("register?error=InvalidPassword");
			} catch (IOException e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
			return;
		}
		
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
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			try {
				resp.sendRedirect("register?error=PasswordMismatch");
			} catch (IOException e) {
				LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
			}
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
		
		try {
			resp.sendRedirect("login");
		} catch (Exception e) {
			LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}

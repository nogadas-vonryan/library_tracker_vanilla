package services;

import java.sql.SQLException;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.User;
import repository.UserRepository;
import servlets.BaseServlet;

public class Auth {
	
	private UserRepository userRepository = new UserRepository();	
	
	public boolean login(BaseServlet baseServlet, HttpServletRequest req, String referenceNumber, String password) throws SQLException {
		User user = userRepository.findByReferenceNumber(baseServlet.conn, referenceNumber);
		
		if (password.equals(user.password)) {
			HttpSession session = req.getSession();
			session.setAttribute("referenceNumber", referenceNumber);
			session.setAttribute("role", user.role);
			baseServlet.logger.info("User logged in: " + referenceNumber + ", role: " + user.role + ", ip: " + req.getRemoteAddr());
			return true;
		} 
		
		return false;
	}
	
	public void logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("referenceNumber");
	}
	
	public void register(BaseServlet baseServlet, String referenceNumber, String password, String firstName,
			String lastName) throws SQLException {
		User user = new User(referenceNumber, password, firstName, lastName);
		
		user.save(baseServlet.conn);
	}
	
	public static boolean isLoggedIn(HttpSession session) {
		return session.getAttribute("referenceNumber") != null;
	}
	
	public static boolean isLoggedIn(HttpServletRequest req) {
		return req.getSession().getAttribute("referenceNumber") != null;
	}
	
	public static boolean isAdmin(HttpServletRequest req) {
		return req.getSession().getAttribute("role").equals("ADMIN");
	}
	
	public static boolean isUser(HttpServletRequest req) {
		return req.getSession().getAttribute("role").equals("USER");
	}
}

package servlets;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.LoggerManager;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {

	private static final long serialVersionUID = 12L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		LoggerManager.logAccess(req, "/logout", "POST");
		
        try {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        } catch (IOException e) {
        	LoggerManager.systemLogger.log(Level.SEVERE, e.getMessage(), e);
        }
	}
	
}

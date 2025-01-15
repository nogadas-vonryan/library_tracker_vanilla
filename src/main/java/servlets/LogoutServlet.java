package servlets;

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
        req.getSession().invalidate();
        handleRedirect(resp, "/login");
	}
}

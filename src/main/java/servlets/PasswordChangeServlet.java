package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.Auth;

@WebServlet("/admin/password-requests/*")
public class PasswordChangeServlet extends BaseServlet {
	private static final long serialVersionUID = 12L;

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
		
		String path = req.getPathInfo();
		if (path == null) {
			try {
				resp.sendRedirect("/admin/password-requests");
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
			return;
		}

		String[] parts = path.split("/");
		String id = parts[1];
		req.setAttribute("id", id);
		
		try {
			forward(req, resp, "admin-password-change");
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String referenceNumber = req.getParameter("referenceNumber");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
	}
}

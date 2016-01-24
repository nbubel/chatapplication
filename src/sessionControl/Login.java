package sessionControl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import messageControl.Protocoll;

/**
 * Servlet, das die Login-Anfrage eines Users bearbeitet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String username = request.getParameter("name");
		String passwort = request.getParameter("passwort");

		if (CheckUser.checkUser(username, passwort)) {
			Protocoll.gebeLogmeldungAus(
					"Erfolgreicher Login-Versuch mit den Daten Name: " + username + ", und Passwort: " + passwort);
			HttpSession userSession = request.getSession();
			userSession.setAttribute("Name", username);
			userSession.setAttribute("admin", false);
			if (CheckUser.checkUser(username)) {
				userSession.setAttribute("admin", true);
			} else{
				userSession.setAttribute("admin", false);
			}
			if (CheckUser.checkBlockedUser(username)) {
				userSession.setAttribute("block", true);
			} else {
				userSession.setAttribute("block", false);
			}
			response.sendRedirect("/CambiChat/Client");
		} else {
			Protocoll.gebeLogmeldungAus(
					"Fehlgeschlagender Login-Versuch mit den Daten Name: " + username + ", und Passwort: " + passwort);
			request.getRequestDispatcher("Login.html").include(request, response);
		}
	}
}

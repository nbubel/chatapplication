package sessionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import messageControl.Protocoll;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Admin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {
			// String username=(String) session.getAttribute("Name");
			URL doc;
			HttpSession userSession = request.getSession();
			if (userSession.getAttribute("admin").equals(true)) {
				doc = new URL("http://tomcat/CambiChat/admin2.html");
			} else {
				doc = new URL("http://tomcat/CambiChat/admin.html");
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(doc.openStream(), "UTF8"));
			String htmlOut = "";
			String temp;

			try {
				while ((temp = bf.readLine()) != null) {
					htmlOut += (temp + "\n");
				}
				Protocoll.gebeLogmeldungAus("HTML-Doc für die Einstellungen wurde initialisiert.");
				bf.close();
			} catch (IOException e) {
				Protocoll.gebeLogmeldungAus("Beim Einlesen des HTML-Docs trat ein Fehler auf: " + e);
				e.printStackTrace();
			}

			out.print(htmlOut);
		} else {
			request.getRequestDispatcher("Login.html").include(request, response);
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String adminSetting = request.getParameter("AdminSettingsSelect");
		String username = request.getParameter("username");
		String passwort = request.getParameter("passwort");

		if (adminSetting != null) {
			if (adminSetting.equals("newUser")) {
				Protocoll.gebeLogmeldungAus("Der Admin möchte folgende Aktion ausführen", adminSetting);
				Protocoll.gebeLogmeldungAus("für Nutzer00", username);
				Protocoll.gebeLogmeldungAus("mit passwort", passwort);
				dbControl.User.newUser(username, passwort);
			} else if (adminSetting.equals("deleteUser")) {
				Protocoll.gebeLogmeldungAus("Der Admin möchte folgende Aktion ausführen", adminSetting);
				Protocoll.gebeLogmeldungAus("für Nutzer", username);
				dbControl.User.deleteUser(username);
			} else if (adminSetting.equals("blockUser")){
				Protocoll.gebeLogmeldungAus("Der Admin möchte folgende Aktion ausführen", adminSetting);
				Protocoll.gebeLogmeldungAus("für Nutzer", username);
				dbControl.User.blockUser(username);
			}
		}

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {
			// String username=(String) session.getAttribute("Name");
			URL doc;
			HttpSession userSession = request.getSession();
			if (userSession.getAttribute("admin").equals(true)) {
				doc = new URL("http://tomcat/CambiChat/admin2.html");
			} else {
				doc = new URL("http://tomcat/CambiChat/admin.html");
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(doc.openStream(), "UTF8"));
			String htmlOut = "";
			String temp;

			try {
				while ((temp = bf.readLine()) != null) {
					htmlOut += (temp + "\n");
				}
				Protocoll.gebeLogmeldungAus("HTML-Doc für die Einstellungen wurde initialisiert.");
				bf.close();
			} catch (IOException e) {
				Protocoll.gebeLogmeldungAus("Beim Einlesen des HTML-Docs trat ein Fehler auf: " + e);
				e.printStackTrace();
			}

			out.print(htmlOut);
		} else {
			request.getRequestDispatcher("Login.html").include(request, response);
		}
		out.close();
	}

}

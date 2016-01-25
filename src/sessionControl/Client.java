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
 * Gibt das Chatfenster aus, wenn der User eingelogt ist!
 */
@WebServlet("/Client")
public class Client extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Client() {
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
		URL doc;
		if (session != null) {
			
			HttpSession userSession = request.getSession();
			if (userSession.getAttribute("admin").equals(true)){
				 doc = new URL("http://tomcat/CambiChat/Client2.html");
			} 
			else {
				doc = new URL("http://tomcat/CambiChat/Client.html");
			}
			if (userSession.getAttribute("block").equals(true)){
				 doc = new URL("http://tomcat/CambiChat/Client3.html");
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(doc.openStream(), "UTF8"));
			String htmlOut = "";
			String temp;

			try {
				while ((temp = bf.readLine()) != null) {
					htmlOut += (temp + "\n");
				}
				Protocoll.gebeLogmeldungAus("HTML-Doc fuer das Chatfenster wurde initialisiert.");
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

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
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);  
		
		if (session!= null) {
			String username=(String) session.getAttribute("Name");
			URL doc = new URL("http://tomcat/CambiChat/Logout.html");
			BufferedReader bf = new BufferedReader(new InputStreamReader(doc.openStream(), "UTF8"));
			String htmlOut = "";
			String temp;

			try {
				while ((temp = bf.readLine()) != null) {
					htmlOut += (temp + "\n");
				}
				Protocoll.gebeLogmeldungAus("HTML-Doc für das Chatfenster wurde initialisiert.");
				bf.close();
			} catch (IOException e) {
				Protocoll.gebeLogmeldungAus("Beim Einlesen des HTML-Docs trat ein Fehler auf: " + e);
				e.printStackTrace();
			}
			
			session.invalidate();
			out.print(htmlOut);
			Protocoll.gebeLogmeldungAus(
					"Erfolgreicher Logout des Users: " + username);
		} else {
			response.sendRedirect("Login.html");
		}
		out.close();
	}

}

package messageControl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;



/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/ChatControl")
public class ChatControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ArrayList<Message> MessageListe;
	Protocoll Logfile = new Protocoll();
	MessageId id;
 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ChatControl() {
        super();
        MessageListe = new ArrayList<Message>();
        id = new MessageId();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int lastId = Integer.parseInt(request.getParameter("id"));
		Protocoll.gebeLogmeldungAus("letzte Nachricht", lastId);
		int messagezaehler = 0;
		response.setContentType("text/xml");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<messages xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xsi:schemaLocation=\"http://localhost:8080/CambiChat/schema.xsd\">");
		Protocoll.gebeLogmeldungAus("Messageid", id.getMessageId());
		for (Message m : MessageListe) {
			if ( m.getId()>= lastId){
			out.println("<message><id>"+ m.getId() +"</id><date>"+ m.getDate()
			+ "</date><time>" + m.getTime() +"</time><name>" + m.getName()+"</name><nachricht>" +m.getMessage()+"</nachricht>"
					+ "</message>");
			Protocoll.gebeLogmeldungAus("Nachricht wird ausgegenem mit der id", m.getId() );
			messagezaehler ++;
			continue;
			} else {
				Protocoll.gebeLogmeldungAus("Keine neuen Nachrichten vorhanden!");
				continue;
			}
		}
		out.println("</messages>");
		out.close();
		Protocoll.gebeLogmeldungAus("Anzahl der Nachrichten für die Ausgabe", messagezaehler);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream xmlNachricht = request.getInputStream();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document message = null;
		try {
			message = dbFactory.newDocumentBuilder().parse(xmlNachricht);
			Protocoll.gebeLogmeldungAus("XML-Message-Input wurde geparst.");
		} catch (SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		String name = message.getElementsByTagName("name").item(0).getTextContent();
		String nachricht = message.getElementsByTagName("nachricht").item(0).getTextContent();
		String time = message.getElementsByTagName("time").item(0).getTextContent();
		String date = message.getElementsByTagName("date").item(0).getTextContent();

		Protocoll.gebeLogmeldungAus(name, nachricht, time, date);
		
		id.incrementMessageId(1);
		Message m = new Message(name, nachricht, time, date, id.getMessageId());
		MessageListe.add(m);
		
		Protocoll.gebeLogmeldungAus("Add Message to List!", m.getId());
	}
}

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
 * 
 *  @author: Niels Bubel, mail: mail@niels-bubel.de
 *  @version: 09.12.2015
 * 
 */
@WebServlet("/ChatControl")
public class ChatControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	ArrayList<Message> messageList = new ArrayList<Message>();
	MessageId id = new MessageId();
 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ChatControl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int lastId = Integer.parseInt(request.getParameter("id"));
		String groupId = request.getParameter("group");
		boolean save = Boolean.parseBoolean(request.getParameter("save"));
		Protocoll.gebeLogmeldungAus("SaveRequest", save);
		Protocoll.gebeLogmeldungAus("letzte Nachricht", lastId);
		Protocoll.gebeLogmeldungAus("GruppenID", groupId);
		int messagezaehler = 0;
		response.setContentType("text/xml");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<messages xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xsi:schemaLocation=\"http://localhost:8080/CambiChat/schema.xsd\">");
		Protocoll.gebeLogmeldungAus("Messageid", id.getMessageId());
		if (save){
			for (Message m : messageList) {
				if (m.getGroup().equals(groupId)){
					Protocoll.gebeLogmeldungAus("saveMessage for", m.getId());
					boolean savem = dbControl.Messages.saveMessage(m);
					if (savem){
						Protocoll.gebeLogmeldungAus("Message saved", m.getId());
					}
				}
			}
		}
		for (Message m : messageList) {
			if ( m.getId()>= lastId && m.getGroup().equals(groupId) || groupId.equals("all")){
			out.println("<message><id>"+ m.getId() +"</id><date>"+ m.getDate()
			+ "</date><time>" + m.getTime() +"</time><name>" + m.getName()+"</name><nachricht>" +m.getMessage()+"</nachricht>"
					+ "<group>" +m.getGroup()+"</group>"
					+ "</message>");
			Protocoll.gebeLogmeldungAus("Nachricht wird ausgegeben mit der id", m.getId());
			Protocoll.gebeLogmeldungAus("Nachricht wird ausgegeben für die Group", m.getGroup());
			messagezaehler ++;
			continue;
			} else {
				Protocoll.gebeLogmeldungAus("Keine neuen Nachrichten vorhanden!");
				continue;
			}
		}
		out.println("</messages>");
		out.close();
		Protocoll.gebeLogmeldungAus("Anzahl der Nachrichten fuer die Ausgabe", messagezaehler);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean setting = Boolean.parseBoolean(request.getParameter("setting"));
		
		InputStream xmlNachricht = request.getInputStream();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document message = null;
		try {
			message = dbFactory.newDocumentBuilder().parse(xmlNachricht);
			Protocoll.gebeLogmeldungAus("XML-Message-Input wurde geparst.");
		} catch (SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		if (!setting){		
		String name = message.getElementsByTagName("name").item(0).getTextContent();
		String nachricht = message.getElementsByTagName("nachricht").item(0).getTextContent();
		String time = message.getElementsByTagName("time").item(0).getTextContent();
		String date = message.getElementsByTagName("date").item(0).getTextContent();
		String group = message.getElementsByTagName("group").item(0).getTextContent();

		Protocoll.gebeLogmeldungAus(name, nachricht, time, date, group);
		
		id.incrementMessageId(1);
		Message m = new Message(name, nachricht, time, date, id.getMessageId(), group);
		messageList.add(m);
		
		Protocoll.gebeLogmeldungAus("Add Message to List!", m.getId());
		}
		else {
			String ops = message.getElementsByTagName("setting").item(0).getTextContent();
			String group = message.getElementsByTagName("group").item(0).getTextContent();
			Protocoll.gebeLogmeldungAus("Do setting", ops);
			Protocoll.gebeLogmeldungAus("for group", group);
			if (ops.equals("load")){
				Protocoll.gebeLogmeldungAus("Start reading out of db");
				ArrayList<Message> loadedMessageList = dbControl.Messages.getMessages(group);
				for (Message m : loadedMessageList) {
					messageList.add(m);
				}
			}
			else {
				Protocoll.gebeLogmeldungAus("Start delete out of List");
				ArrayList<Message> tempList = new ArrayList<Message>();
				for (Message m : messageList) {
					if (!m.getGroup().equals(group)){
						Protocoll.gebeLogmeldungAus("deleted Message in List", m.getMessage());
						tempList.add(m);
					}
				}
				messageList = tempList;
				
				Protocoll.gebeLogmeldungAus("Start delete out of db");
				dbControl.Messages.deleteMessages(group);
			}
		}
	}
}

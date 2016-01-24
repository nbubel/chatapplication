package dbControl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import messageControl.Message;
import messageControl.Protocoll;

public class Messages {

	public static ArrayList<Message> getMessages(String group) {

		ArrayList<Message> messageListe = new ArrayList<Message>();
		Protocoll.gebeLogmeldungAus("Start to read in ArrayList");

		try {
			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			ResultSet result;
			result = statement.executeQuery("select * from messages where gruppe like '" + group +"'");
			// Protocoll.gebeLogmeldungAus("Ergebnis der User-Abfrage:",
			// result);

			while (result.next()) {

				Message m = new Message(result.getString("name"), result.getString("nachricht"),
						result.getString("time"), result.getString("date"), result.getInt("nr"),
						result.getString("gruppe"));
				messageListe.add((Message) m);
			}
			for (Message m : messageListe) {
				Protocoll.gebeLogmeldungAus(
						"Message " + m.getId() + " von " + m.getName() + " mit dem Text: " + m.getMessage());
			}
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return messageListe;
	}
	
	public static boolean saveMessage(Message m) {
		Protocoll.gebeLogmeldungAus("Speichere Nachricht in der Datenbank von " + m.getName());
		
		try {
		Connection connection = dbControl.DBConnection.getInstance().dbConnection;
		Statement statement = connection.createStatement();
		statement.executeUpdate("INSERT INTO messages VALUES (null,'" + m.getName() + "','" + m.getMessage() +"','" + m.getTime() + "', '" + m.getDate() + "'," + m.getId() + ",'" + m.getGroup() +"')");
		
		connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;	
		
	}
	
	
	public static boolean deleteMessages(String group) {
		Protocoll.gebeLogmeldungAus("Lösche Nachricht in der Datenbank der Gruppe " + group);
		
		try {
		Connection connection = dbControl.DBConnection.getInstance().dbConnection;
		Statement statement = connection.createStatement();
		statement.executeUpdate("Delete From messages where gruppe like '" + group + "' ");
		
		connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;	
		
	}

}

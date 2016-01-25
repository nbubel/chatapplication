package dbControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import messageControl.Protocoll;

/**
 * Datenbank-CRUD-Operationen für die User des Chats
 * 
 * @author Niels
 *
 */
public class User {

	/**
	 * Löscht einen User aus der Datenbank
	 * 
	 * @param username
	 * @return boolean
	 */
	public static boolean deleteUser(String username) {
		Protocoll.gebeLogmeldungAus("Loesche User in der Datenbank mit dem Namen" + username);

		try {
			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			statement.executeUpdate("Delete From user where name like '" + username + "' ");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Legt einen neuen User in der Datnebank an
	 * 
	 * @param username
	 * @param passwort
	 * @return boolean
	 */
	public static boolean newUser(String username, String passwort) {
		Protocoll.gebeLogmeldungAus("Neuer User in der Datenbank mit dem Namen" + username);
		Protocoll.gebeLogmeldungAus("Neuer User in der Datenbank mit dem Passwort" + passwort);

		try {
			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO user VALUES (null,'" + username + "', '" + passwort + "', 0 , 0)");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Blockiert einen User: Er kann sich dann nicht mehr anmelden!
	 * 
	 * @param username
	 * @return boolean
	 */
	public static boolean blockUser(String username) {
		Protocoll.gebeLogmeldungAus("Blockiere User in der Datenbank mit dem Namen" + username);

		try {
			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			statement.executeUpdate("Update user set sperre=1 where name like '" + username + "'");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}

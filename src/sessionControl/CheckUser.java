/**
 * 
 */
package sessionControl;

import java.sql.*;

import messageControl.Protocoll;

/**
 * Prüft, ob ein User oder ein Admin sich mit korrekten Daten, die in der Datenbank stehen,
 * angemeldet hat.
 * 
 * @author Niels
 *
 */
public class CheckUser {

	/**
	 * Prüft, ob ein User sich mit korrekten Daten, die in der Datenbank stehen,
	 * angemeldet hat.
	 * 
	 * @param name
	 * @param passwort
	 * @return boolean
	 */
	public static boolean checkUser(String name, String passwort) {
		boolean check = false;
		try {
			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			PreparedStatement statement = connection.prepareStatement("select * from user where name=? and password=?");
			statement.setString(1, name);
			statement.setString(2, passwort);
			ResultSet result = statement.executeQuery();
			// Protocoll.gebeLogmeldungAus("Ergebnis der User-Abfrage:",
			// result);
			check = result.next();
			Protocoll.gebeLogmeldungAus("Ergebnis der User-Abfrage", check);
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

	/**
	 * Prüft, ob ein User ein Admin ist
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkUser(String name) {
		boolean check = false;
		try {

			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			ResultSet result;
			result = statement.executeQuery("select admin from user where name like'" + name + "'");
			while (result.next()) {
				if (result.getString("admin").equals("1")) {
					check = true;
				}
			}
			Protocoll.gebeLogmeldungAus("Ergebnis der Admin-Abfrage", check);
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

	/**
	 * Prüft, ob ein User gesperrt wurde.
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkBlockedUser(String name) {
		boolean check = false;
		try {

			Connection connection = dbControl.DBConnection.getInstance().dbConnection;
			Statement statement = connection.createStatement();
			ResultSet result;
			result = statement.executeQuery("select sperre from user where name like'" + name + "'");
			while (result.next()) {
				if (result.getString("sperre").equals("1")) {
					check = true;
				}
			}
			Protocoll.gebeLogmeldungAus("Ergebnis der Block-Abfrage", check);
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}

}

/**
 * 
 */
package dbControl;

import java.sql.*;

/**
 * Baut nach dem Singleton Pattern eine
 * Datenbankverbindung auf und stellt eine Instanz über
 * die getInstance-Methode bereit
 * 
 * @author Niels
 *
 */
public class DBConnection {

	public static Connection dbConnection;
	private static DBConnection conn;

	/**
	 *  private Konstruktor
	 */
	private DBConnection() {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cambi", "cambidb", "cambicambi");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt eine Instanz der Datenbankverbindung zurück
	 * 
	 * @return Datenbankverbindung
	 */
	public static DBConnection getInstance() {
		try {
			if (dbConnection == null || dbConnection.isClosed()) {
				synchronized (DBConnection.class) {
					if (dbConnection == null || dbConnection.isClosed()) {
						conn = new DBConnection();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 *  Schließt die Datenbankverbindung nach dem
	 *  ausführen der Operationen
	 */
	public void close() {
		if (dbConnection != null)
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		conn = null;
		dbConnection = null;
	}

}

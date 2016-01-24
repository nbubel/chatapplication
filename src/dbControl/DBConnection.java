/**
 * 
 */
package dbControl;

import java.sql.*;

/**
 * @author Niels
 *
 */
public class DBConnection {

	public static Connection dbConnection;
	private static DBConnection conn;


	private DBConnection() {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cambi", "cambidb",
					"cambicambi");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBConnection getInstance() {
		try {
			if (dbConnection == null || dbConnection.isClosed()) {
				synchronized (DBConnection.class) {
					conn = new DBConnection();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public synchronized void close() {
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

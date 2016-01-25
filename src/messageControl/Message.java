package messageControl;

import java.io.Serializable;

/**
 * Enthaelt die Nachrichten-Daten
 *
 * 
 * @author Niels
 */

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String message;
	private String time;
	private String date;
	private int id;
	private String group;

	/**
	 * Nachrichten-Konstruktor
	 * 
	 * @param name
	 * @param message
	 * @param time
	 */
	public Message(String name, String message, String time, String date, int id, String group) {
		super();
		this.name = name;
		this.message = message;
		this.time = time;
		this.date = date;
		this.id = id;
		this.group = group;
	}

	/**
	 * Gibt den Sender der Nachricht zurueck
	 * 
	 * @return name
	 */
	public String getName() {
		if (name == null) {
			throw new NullPointerException("Der Name ist nicht vorhanden.");
		}
		return name;
	}

	/**
	 * aendert den Sender der Nachricht
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Veraendert die Uhrzeit der Nachricht
	 * 
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Gibt die Uhrzeit der Nachricht zurueck
	 * 
	 * @return time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Veraendert das Datum der Nachricht
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gibt das Datum der Nachricht zurueck
	 * 
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gibt den Nachrichtentext aus
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Veraendert den Nachrichtentext
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * aendert die ID der Nachricht
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gibt die ID der Nachricht
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gibt den Empfaenger/Gruppennamen aus
	 * 
	 * @return group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Veraendert den Gruppen/Empfaenger-Namen
	 * 
	 * @param group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
}

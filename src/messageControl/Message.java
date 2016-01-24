package messageControl;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String message;
	private String time;
	private String date;
	private int id;
	private String group;
	
	/**
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
	 * @return name
	 */
	public String getName() {
		if (name == null){
			throw new NullPointerException("Der Name ist nicht vorhanden.");
		}
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * @return
	 */
	public String getTime() {
		return time;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param group
	 */
	public String getGroup() {
		return group;
	}
	
	
	/**
	 * @return group
	 */
	public void setGroup(String group) {
		this.group = group;
	}


	public void saveMessage() {
		Protocoll.gebeLogmeldungAus("Speichernachricht mit der ID: " + this.id);
		dbControl.MessagesDB.saveMessage(this);
	}
	
}





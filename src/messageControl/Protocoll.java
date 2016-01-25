package messageControl;

/**
 * Gibt Protocollmeldungen aus
 * 
 * @author Niels Bubel
 *
 */
public class Protocoll {

	/**
	 * Gibt eine Meldung für einen String aus
	 * 
	 * @param name
	 */
	public static void gebeLogmeldungAus(String name) {
		System.out.println(name);
	}

	/**
	 * Gibt eine Meldung für einen String und einen parameter aus
	 * 
	 * @param name
	 * @param parameter
	 */
	public static void gebeLogmeldungAus(String name, Object parameter) {
		System.out.println(name + ": " + parameter);
	}

	/**
	 * Gibt eine Meldung für ein Nachrichtenobjekt aus!
	 * 
	 * @param name
	 * @param nachricht
	 * @param time
	 * @param date
	 * @param group
	 */
	public static void gebeLogmeldungAus(String name, String nachricht, String time, String date, String group) {
		System.out.println("Get Name: " + name);
		System.out.println("Get Message: " + nachricht);
		System.out.println("Get Date: " + time);
		System.out.println("Get Time: " + date);
		System.out.println("Get Group: " + group);
	}

}
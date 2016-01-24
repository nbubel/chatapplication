package messageControl;

public class Protocoll {
	
	public static void gebeLogmeldungAus(String name) {
		System.out.println(name);
	}
	
	public static void gebeLogmeldungAus(String name, Object parameter) {
		System.out.println(name + ": " + parameter);
	}
	
	public static void gebeLogmeldungAus(String name, String nachricht, String time, String date, String group) {
		System.out.println("Get Name: " + name);
		System.out.println("Get Message: " + nachricht);
		System.out.println("Get Date: " + time);
		System.out.println("Get Time: " + date);
		System.out.println("Get Group: " + group);
	}

}
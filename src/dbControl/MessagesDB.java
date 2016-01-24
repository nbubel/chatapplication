package dbControl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import messageControl.Message;
import messageControl.Protocoll;

public class MessagesDB {

	public static void saveMessage(Message message) {
		Protocoll.gebeLogmeldungAus("Message wird gespeichert!");
		Protocoll.gebeLogmeldungAus("Message " + message.getId() + " von " + message.getName()
					+ " mit dem Text: " + message.getMessage());
		try {
			FileOutputStream fos = new FileOutputStream("messages.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(message);
			oos.close();
		} catch (Exception e) {
			System.err.println("Speichern der Daten war nicht erfolgreich: " + e);
		}
	}

	public static ArrayList<Message> readAllMessages() throws IOException {
		ArrayList<Message> messageListe = new ArrayList<Message>();
		Protocoll.gebeLogmeldungAus("Start to read in ArrayList");
		FileInputStream fis = new FileInputStream("messages.ser");
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			while (obj != null) {
				messageListe.add((Message) obj);				
			}
			fis.close();
			Protocoll.gebeLogmeldungAus("Messages wurden aus Datei eingelesen!");
			for (Message m : messageListe) {
				Protocoll.gebeLogmeldungAus("Message " + m.getId() + " von " + m.getName()
						+ " mit dem Text: " + m.getMessage());
			}
		} catch (Exception e) {
			System.err.println(" Datei nicht vorhanden oder leer. Es wird eine leere Message List übergeben");
			messageListe = new ArrayList<Message>();
		}
		return messageListe;
	}

}

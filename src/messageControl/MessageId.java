package messageControl;

/**
 * Definiert die MessageID
 * 
 * @author Niels
 *
 */
public class MessageId {
	
	int messageId;
	
	
	public MessageId() {
		this.messageId = 0;
	}
	
	/**
	 * Gibt die MessageID zurück
	 * 
	 * @return MessageID
	 */
	public int getMessageId() {
		return messageId;
	}
	
	/**
	 * Erhöht die Message ID um den Wert, der übergeben wird
	 * 
	 * @param increment
	 */
	public void incrementMessageId(int increment) {
		this.messageId = this.messageId + increment;
	}

}

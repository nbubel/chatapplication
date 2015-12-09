/**
 * 
 */
package messageControl;

/**
 * @author Niels
 *
 */
public class MessageId {
	
	int messageId;
	
	public MessageId() {
		messageId = 0;
	}
	
	public int getMessageId() {
		return messageId;
	}
	
	public void incrementMessageId(int increment) {
		this.messageId = this.messageId + increment;
	}

}

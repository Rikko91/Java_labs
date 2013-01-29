import java.io.Serializable;

enum TypeOfMessage implements Serializable {
	Text,
	Disconnect;
	
}
public class SendObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private int type;
	private TypeOfMessage typeMessage;
	
	public SendObject (String message, int type) {
		this.message = message;
		this.type = type;
		
	}

	public String getMessage() {
		return message;
	}

	public TypeOfMessage getTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(TypeOfMessage typeMessage) {
		this.typeMessage = typeMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
}



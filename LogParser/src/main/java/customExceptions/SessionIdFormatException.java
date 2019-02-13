package customExceptions;

import logview.PopupWindow;

public class SessionIdFormatException extends LogfileParserException{


	private static final long serialVersionUID = -8027598433228443793L;
	
	public SessionIdFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {
		
		PopupWindow.error("Error", "Error while parsing the sessionId at entry number " + ENTRY_INDEX);
		
	}

}

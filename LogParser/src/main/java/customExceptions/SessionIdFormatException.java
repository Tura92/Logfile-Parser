package customExceptions;

import logview.Meldungsfenster;

public class SessionIdFormatException extends LogfileParserException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8027598433228443793L;

	@Override
	public void showMessageForUser() {
		
		String errorMessage = "Fehler beim parsen der SessionID";
		Meldungsfenster.error(head, errorMessage);
		
	}

}

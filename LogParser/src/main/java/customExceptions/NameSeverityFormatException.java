package customExceptions;

import logview.Meldungsfenster;

public class NameSeverityFormatException extends LogfileParserException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6383812283423116787L;

	@Override
	public void showMessageForUser() {
		
		String errorMessage = "Fehler beim parsen des Namen oder der Severity";
		Meldungsfenster.error(head, errorMessage);
		
	}
}

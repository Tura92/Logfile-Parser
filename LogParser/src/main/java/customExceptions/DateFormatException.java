package customExceptions;

import logview.Meldungsfenster;

public class DateFormatException extends LogfileParserException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5777434394847489692L;

	@Override
	public void showMessageForUser() {
		
		String errorMessage = "Fehler beim parsen des Datums";
		Meldungsfenster.error(head, errorMessage);
		
	}

}

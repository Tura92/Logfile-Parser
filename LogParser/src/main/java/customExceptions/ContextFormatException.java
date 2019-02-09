package customExceptions;

import logview.Meldungsfenster;

public class ContextFormatException extends LogfileParserException{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2948971869685436143L;

	@Override
	public void showMessageForUser() {
		
		String errorMessage = "Fehler beim parsen des Context";
		Meldungsfenster.error(head, errorMessage);
		
	}

	

}

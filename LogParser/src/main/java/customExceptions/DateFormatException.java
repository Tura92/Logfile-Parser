package customExceptions;

import logview.PopupWindow;

public class DateFormatException extends LogfileParserException {
	

	private static final long serialVersionUID = -5777434394847489692L;
	
	public DateFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {
		
		PopupWindow.error("Error", "Error while parsing the date at entry number " + ENTRY_INDEX);
		
	}

}

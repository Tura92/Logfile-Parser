package customExceptions;

import logview.PopupWindow;

public class NameFormatException extends LogfileParserException{
	

	private static final long serialVersionUID = 8621701170585484903L;

	public NameFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {

		PopupWindow.error("Error", "Error while parsing the name at entry number " + ENTRY_INDEX);
		
	}

}

package customExceptions;

import logview.PopupWindow;

public class TextFormatException extends LogfileParserException{

	
	private static final long serialVersionUID = 5044564601906305642L;

	public TextFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {
		
		PopupWindow.error("Error", "Error while parsing the text at entry number " + ENTRY_INDEX);
		
	}
	
}

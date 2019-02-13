package customExceptions;

import logview.PopupWindow;

public class ContextFormatException extends LogfileParserException{
		

	private static final long serialVersionUID = -2948971869685436143L;
	
	public ContextFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {
		
		PopupWindow.error("Error", "Error while parsing the context at entry number " + ENTRY_INDEX);
		
	}

}

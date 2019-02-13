package customExceptions;

import logview.PopupWindow;

public class SeverityFormatException extends LogfileParserException{


	private static final long serialVersionUID = -6383812283423116787L;
	
	public SeverityFormatException(int ENTRY_INDEX) {
		
		this.ENTRY_INDEX = ENTRY_INDEX;
		
	}
	
	@Override
	public void showMessageForUser() {
		
		PopupWindow.error("Error", "Error while parsing the severity at entry number " + ENTRY_INDEX);
		
	}
	
}

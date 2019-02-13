package customExceptions;

public abstract class LogfileParserException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5895399752510151337L;
	protected int ENTRY_INDEX;
	public abstract void showMessageForUser();
}

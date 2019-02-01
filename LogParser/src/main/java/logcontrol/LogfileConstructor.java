package logcontrol;

import logdata.LogfileEntity;

public class LogfileConstructor {
	
	public static String construct(LogfileEntity le) {
		
		String constructed = "["+le.getDate()+"] ["+le.getSessionId()+"] "
				+le.getAppName()+"."+le.getSeverity()+": "+le.getText()+" ["
				+le.getContext()+"]";
		return constructed;
	}
}

package logdata;

import java.util.ArrayList;

public class LogfileConstructor {
	
	public static String construct(LogfileEntity le) {
		
		String constructed = "["+le.getDate()+"] ["+le.getSessionId()+"] "
				+le.getAppName()+"."+le.getSeverity()+": "+le.getText()+" ["
				+le.getContext()+"]";
		return constructed;
	}
	
	public static ArrayList<String> constructAll(ArrayList<LogfileEntity> ale) {
		
		ArrayList<String> allConstructed = new ArrayList<String>();
		ale.forEach(entity -> allConstructed.add(construct(entity)));
		return allConstructed;
	}
}

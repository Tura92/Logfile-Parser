package logcontrol;

import java.util.ArrayList;

import logdata.LogfileEntry;
import lombok.Getter;

/**
 * Diese Klasse ist der eigentlich Parser für die .log Dateien. 
 * Er besitzt nur eine statische Methode die die rohen Strings
 * aus den logs extrahiert und ein gespaltetes und verarbeitbares
 * Format wieder ausgibt.
 * **/
@Getter
public class LogfileParser {
		
	public ArrayList<LogfileEntry> parseData(ArrayList<String> rawEntities) {
		
		ArrayList<LogfileEntry> logFileEntities = new ArrayList<>();
		
		String date = null;
		String sessionId= null;
		String appName= null;
		String severity = null;
		String text= null;
		String context= null;
		
		
		//res is the raw Data String for a log entity
		for(String res : rawEntities) {
			
			date = res.substring(1, res.indexOf("]"));		
			res = res.substring(res.indexOf("] ")+2);
			
			sessionId = res.substring(1, res.indexOf("]"));
			res = res.substring(res.indexOf("] ")+2);
			
			appName = res.substring(0, res.indexOf("."));
			res = res.substring(res.indexOf(".")+1);
			
			severity = res.substring(0, res.indexOf(":"));
			res = res.substring(res.indexOf(":")+2);
			
			text = res.substring(0, res.indexOf("[") -1);
			res = res.substring(res.indexOf("["));
			
			context = res.substring(1, res.length()-1);
			
			//logFileEntities.add(buildEntity(date, sessionId, appName, severity, text, context));
			logFileEntities.add(LogfileEntry.builder()
					.date(date)
					.sessionId(sessionId)
					.appName(appName)
					.severity(severity)
					.text(text)
					.context(context)
					.build()
			);
			
			
		}
		
		return logFileEntities;
	}
	
	public String parseBack(LogfileEntry le) {
		String reconstructed = "["+le.getDate()+"] ["+le.getSessionId()+"] "
				+le.getAppName()+"."+le.getSeverity()+": "+le.getText()+" ["
				+le.getContext()+"]";
		return reconstructed;
	}
}

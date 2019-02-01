package logcontrol;


import java.util.ArrayList;

import logdata.LogfileEntity;
import lombok.Getter;

@Getter
public class LogfileParser {
		
	public static ArrayList<LogfileEntity> parseData(ArrayList<String> rawEntities) {
		
		ArrayList<LogfileEntity> logFileEntities = new ArrayList<>();
		
		String date = null;
		String sessionId= null;
		String appName= null;
		String severity = null;
		String text= null;
		String context= null;
		
		//res is the raw Data String for a log entity
		for(String res : rawEntities) {
			
			date = res.substring(1, res.indexOf("]"));		
			res = res.substring(res.indexOf("]")+2);
			
			sessionId = res.substring(1, res.indexOf("]"));
			res = res.substring(res.indexOf("]")+2);
			
			appName = res.substring(0, res.indexOf("."));
			res = res.substring(res.indexOf(".")+1);
			
			severity = res.substring(0, res.indexOf(":"));
			res = res.substring(res.indexOf(":")+2);
			
			text = res.substring(0, res.indexOf("[") -1);
			res = res.substring(res.indexOf("["));
			
			context = res.substring(1, res.length()-1);
			
			//logFileEntities.add(buildEntity(date, sessionId, appName, severity, text, context));
			logFileEntities.add(LogfileEntity.builder()
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
}

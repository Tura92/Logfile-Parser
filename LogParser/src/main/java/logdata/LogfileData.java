package logdata;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class LogfileData {
	
	private String date;
	private String sessionId;
	private String appName;
	private String severity;
	private String text;
	private String context;
	
	public LogfileData(String date, String sessionId, String appName, 
			String severity, String text, String context) {
		
		this.date = date;
		this.sessionId = sessionId;
		this.appName = appName;
		this.severity = severity;
		this.text = text;
		this.context = context;
	}
}

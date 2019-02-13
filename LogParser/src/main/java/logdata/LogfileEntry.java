package logdata;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class LogfileEntry {
	
	private Date date;
	private String sessionId;
	private String appName;
	private Severity severity;
	private String text;
	private String context;
	
	public String reconstruct() {
		
		String datePattern = "yyyy-MM-dd' 'HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		
		String reconstructed = "["+dateFormat.format(date)+"] ["+this.getSessionId()+"] "
				+this.getAppName()+"."+this.getSeverity()+": "+this.getText()+" ["
				+this.getContext()+"]";
		return reconstructed;
	}
}

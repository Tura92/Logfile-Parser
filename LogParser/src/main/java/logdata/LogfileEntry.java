package logdata;

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
	
}

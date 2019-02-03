package logdata;

import lombok.Builder;
import lombok.Data;

/**
 *Entity 
 * */
@Data @Builder public class LogfileEntry {
	
	private String date;
	private String sessionId;
	private String appName;
	private String severity;
	private String text;
	private String context;
	
}

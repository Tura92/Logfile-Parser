package logdata;

import logcontrolOLD.LogfileParser;
import lombok.Builder;
import lombok.Data;

/**
 *Entity 
 * */
@Data @Builder public class LogfileEntity {
	
	private String date;
	private String sessionId;
	private String appName;
	private String severity;
	private String text;
	private String context;
	
	@Override
	public String toString() {
		return LogfileParser.construct(this);
	}
	
}

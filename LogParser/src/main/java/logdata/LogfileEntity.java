package logdata;

import logcontrol.LogfileConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogfileEntity {
	
	private String date;
	private String sessionId;
	private String appName;
	private String severity;
	private String text;
	private String context;
	
	@Override
	public String toString() {
		return LogfileConstructor.construct(this);
	}
	
}

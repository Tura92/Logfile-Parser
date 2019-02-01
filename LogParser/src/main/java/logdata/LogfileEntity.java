package logdata;

import logcontrol.LogfileConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * Diese Klasse beinhaltet alle Attribute die aus den Logfiles
 * extrahiert werden können und die überschriebene Methode toString()
 * um diese wieder zu einem Logfileeintrag zu machen.
 * */
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

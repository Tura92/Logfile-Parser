package logdata;

import java.io.BufferedReader;

import lombok.Builder;
import lombok.Data;

@Data @Builder public class LogfileData {
	
	BufferedReader br;
	
	private String date;
	private String sessionId;
	private String appName;
	private String severity;
	private String text;
	private String context;
		 
}

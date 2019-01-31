package logdata;

import java.io.IOException;
import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;

@Data public class LogfileParser {
	
	private LogfileReader logReader;;
	@Getter
	private ArrayList<String> rawDataStrings;
	private ArrayList<LogfileData> data;
	
	public LogfileParser() {
		try {
			initLists();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initLists() throws IOException {
		
		logReader = new LogfileReader("logfile.log");
		
		rawDataStrings = logReader.getFileData();
		data = new ArrayList<>();
	}
	
	public LogfileData buildData(String date, String sessionId, String appName, 
					String severity, String text, String context) {
		
		return new LogfileData(date, sessionId, appName, severity, text, context);
	}
	
	 public void parseData() {
		String date = null;
		String sessionId= null;
		String appName= null;
		String severity = null;
		String text= null;
		String context= null;
		
		//rds is the raw Data String for a log entry
		for(String rds : rawDataStrings) {
			
			date = rds.substring(1, rds.indexOf("]"));
			rds = rds.substring(rds.indexOf("]")+2);
			
			sessionId = rds.substring(1, rds.indexOf("]"));
			rds = rds.substring(rds.indexOf("]")+2);
			
			appName = rds.substring(0, rds.indexOf("."));
			rds = rds.substring(rds.indexOf(".")+1);
			
			severity = rds.substring(0, rds.indexOf(":"));
			rds = rds.substring(rds.indexOf(":")+2);
			
			text = rds.substring(0, rds.indexOf("[") -1);
			rds = rds.substring(rds.indexOf("["));
			
			context = rds.substring(1, rds.length()-1);
			
			data.add(buildData(date, sessionId, appName, severity, text, context));
			
		}
	}
	 
	
	
}

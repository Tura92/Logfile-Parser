package logdata;

import java.io.IOException;
import java.util.ArrayList;

import lombok.Data;
import lombok.NonNull;

@Data public class LogfileParser {
	
	private LogfileReader logReader;;
	
	ArrayList<String> rawDataStrings;;
	ArrayList<LogfileData> data;
	
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
	
	 public void parseData() {
		String date = null;
		String sessionId= null;
		String appName= null;
		String severity = null;
		String text= null;
		String context= null;
		
		for(String rds : rawDataStrings) {
			System.out.println(rds);
			date = rds.substring(1, rds.indexOf("]"));
			rds = rds.substring(rds.indexOf("]")+2);
			//System.out.println(rds);
			sessionId = rds.substring(1, rds.indexOf("]"));
			rds = rds.substring(rds.indexOf("]")+2);
			//System.out.println(rds);
			appName = rds.substring(0, rds.indexOf("."));
			rds = rds.substring(rds.indexOf(".")+1);
			
			severity = rds.substring(0, rds.indexOf(":"));
			rds = rds.substring(rds.indexOf(":")+2);
			System.out.println(rds);
			text = rds.substring(0, rds.indexOf("[") -1);
			rds = rds.substring(rds.indexOf("["));
			System.out.println(rds);
			context = rds.substring(1, rds.length()-1);
			
			System.out.println(date+" "+sessionId+" "+appName+" "+severity+" "+text+" "+context);
		}
	
	}
	
}

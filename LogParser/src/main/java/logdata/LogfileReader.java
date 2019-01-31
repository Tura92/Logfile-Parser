package logdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogfileReader {
	BufferedReader br;
	ArrayList<String> rawDataStrings = new ArrayList<>();
	
	public LogfileReader(String logfile) throws IOException  {
		loadFile(logfile);
	}
	
	public void loadFile(String logfile) throws IOException {
		
		br = new BufferedReader(new FileReader(logfile));
		
		String temp;
		
		while((temp = br.readLine()) != null) {
			rawDataStrings.add(temp);
		}
		
		br.close();
	}
	
	public ArrayList<String> getFileData() {
		return rawDataStrings;
	}
	
	
}

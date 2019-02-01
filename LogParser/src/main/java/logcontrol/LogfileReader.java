package logcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Data;

/*
 * 
 * **/
@Data
public class LogfileReader {
	
	BufferedReader br;
	File logfile;
	ArrayList<String> rawEntityStrings = new ArrayList<>();
	
	public LogfileReader(File logfile) {
		this.logfile = logfile;
		
	}
	
	public void loadFile() throws IOException {
		
		br = new BufferedReader(new FileReader(logfile));
		
	}
	
	public void readFile() throws IOException {
		String temp;
		
		while((temp = br.readLine()) != null) {
			rawEntityStrings.add(temp);
		}
		
		br.close();
	}
	
}

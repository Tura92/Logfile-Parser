package logcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Data;

/*
 * Diese Klasse wird benötigt um die Logfiles auszulesen
 * **/
@Data
public class LogfileReader {
	
	BufferedReader br;
	ArrayList<String> rawEntryStrings = new ArrayList<>();

	
	public void loadFile(File logfile) throws IOException {
		
		br = new BufferedReader(new FileReader(logfile));
		
	}
	
	public void readFile() throws IOException {
		String temp;
		
		while((temp = br.readLine()) != null) {
			rawEntryStrings.add(temp);
		}
		
		br.close();
	}
	
}

package control.logcontrol;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import lombok.Data;

/*
 * Diese Klasse wird benötigt um die Logfiles auszulesen
 * **/
@Data
public class LogfileReader {
	
	LineNumberReader lnr;
	ArrayList<String> rawEntryStrings = new ArrayList<>();

	
	public void loadFile(File logfile) throws IOException {
		
		lnr = new LineNumberReader(new FileReader(logfile));
		
	}
	
	public void readFile() throws IOException {
		String temp;
		
		while((temp = lnr.readLine()) != null) {
			if (temp.length() != 0) rawEntryStrings.add(temp);
		}
		
		lnr.close();
	}
	
}

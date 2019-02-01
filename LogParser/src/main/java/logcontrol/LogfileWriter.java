package logcontrol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LogfileWriter {
	
	private static PrintWriter logfileWriter;
	
	public static void writeBackToFile(ArrayList<String> selectedEntities, File destination) throws FileNotFoundException {
		logfileWriter = new PrintWriter(destination);
	    selectedEntities.forEach(s -> logfileWriter.println(s));
	    logfileWriter.close();
	}
}

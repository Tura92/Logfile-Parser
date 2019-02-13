package tablecontrol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import customExceptions.LogfileParserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logcontrol.LogfileParser;
import logcontrol.LogfileReader;
import logcontrol.LogfileWriter;
import logdata.LogfileEntry;
import logview.PopupWindow;


/**Der ListManager ist dafür zuständig, dass die Tabelle
*ordentlich generiert wird und sorgt für die Allgemeine
*Funktionalität des Programmes.
*/ 
public class ListManager {
	//Diese Liste nimmt die Einträge des Logfiles und macht sie Observable
	ObservableList<LogfileEntry> observableEntries;
	
	//Die Liste in der nur die gefilterten Einträge stehen
	FilteredList<LogfileEntry> filteredEntries;
	
	SortedList<LogfileEntry> sortedEntries;
	
	
	public ObservableList<LogfileEntry> getEntries(Stage window) {
		
		observableEntries = FXCollections.observableArrayList();
		
		/*Diese Liste speichert die fertigen Entries um sie am Ende
		*in die ObservableList reinzuschieben
		*/
		ArrayList<LogfileEntry> parsedEntries = new ArrayList<>();
		
		ArrayList<String> rawEntries = new ArrayList<>();
		
		LogfileParser lp = new LogfileParser();
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logfiles (*.log)", "*.log"));
		File logfile = fc.showOpenDialog(window);
		
		
		if(logfile != null) {
			//Initialisierung eines LogfileReaders
			LogfileReader lr = new LogfileReader();
			
			//Hier wird versucht die Datei in den Speicher zu laden
			try {
				
				lr.loadFile(logfile);
				lr.readFile();
						
			} catch (IOException e) {
				
				PopupWindow.error("Error", e.getMessage());					
			}
			
			rawEntries = lr.getRawEntryStrings();
			
			//Der kritischste Teil des Programms in dem alle möglichen Exceptions
			//geworfen werden können wenn die Einträge nicht korrekt sind
			try {
									
				parsedEntries = lp.parseFile(rawEntries);
									
			} catch (LogfileParserException exc) {
				exc.showMessageForUser();	
				
			} catch (Exception exc) {
				PopupWindow.error("Error", exc.getMessage());
			} 				
		}
		
		parsedEntries.forEach(entry -> observableEntries.add(entry));	
		return observableEntries;
	}
	
	
	public void initFiltering(TableView<LogfileEntry> table, TextField filterTF, ComboBox<String> filterCB ) {
		
		filteredEntries = new FilteredList<>(observableEntries, p -> true);
		
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEntries.setPredicate(entry -> {
              
            	String filterValue = "";
            	
            	//Hier wird der Filter umgestellt
            	switch(filterCB.getValue()) {
            		case "SessionID": filterValue = entry.getSessionId(); break;
            		case "AppName": filterValue = entry.getAppName(); break;
            		case "Severity": filterValue = entry.getSeverity().toString(); break;
            	}
            	
            	// Wenn Filtertext leer: zeige alle Logfileentities an
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (filterValue.toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter passt zum filterTF Eintrag.
                } 
                return false; // Passt nicht.
            	       
            });
        });
		
		sortedEntries = new SortedList<>(filteredEntries);
		sortedEntries.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedEntries);
	}
	
	public void saveFile(Stage window, TableView<LogfileEntry> table) {
		
		ArrayList<String> selectedEntities = new ArrayList<>();
		
		LogfileParser lp = new LogfileParser();
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("LOG", "*.log"));
		File destination = fc.showSaveDialog(window);
		
		table.getItems().forEach(entity -> selectedEntities.add(entity.reconstruct()));
	
		if(destination != null && !selectedEntities.isEmpty()) {
			try {
				LogfileWriter.writeBackToFile(selectedEntities, destination);				
				PopupWindow.info("Erfolgreich gespeichert", 
						"Es wurde eine Logdatei mit den modifizierten Ergebnissen erzeugt.");
				
			} catch (FileNotFoundException e) {						
				PopupWindow.error("Error", e.getMessage());
			}
		}
	}		
}

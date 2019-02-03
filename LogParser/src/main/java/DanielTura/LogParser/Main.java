package DanielTura.LogParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logcontrol.LogfileParser;
import logcontrol.LogfileReader;
import logcontrol.LogfileWriter;
import logdata.LogfileEntry;
 
public class Main extends Application {
	
	//Hauptfenster
	Stage window;
	
	//Tabelle zum Anzeigen des Logfiles
	TableView<LogfileEntry> table;
	
	//Load file Button und Save file Button 
	Button loadFileBtn, saveBtn;
	
	//Label das "Filter by:" anzeigt
	Label filterByLbl;
	
	//ComboBox zum Auswählen des Filters
	ComboBox<String> filterCB;
	
	//Das Textfeld in den der gesuchte Eintrag eingegeben wird
	TextField filterTF;
	
	HBox hBox;
	VBox vBox;
	Scene scene;
	
	//Diese Liste nimmt die Einträge des Logfiles und macht sie Observable
	ObservableList<LogfileEntry> observableEntities;
	
	//Die Liste in der nur die gefilterten Einträge stehen
	FilteredList<LogfileEntry> filteredData;
	
	
    public static void main(String[] args) {
        launch(args);
    }
 
	@Override
    public void start(Stage primaryStage) throws FileNotFoundException {
      
       window = primaryStage;
       window.setTitle("Logfile Parser");
       
       initComponents();
       
       window.setScene(scene);
       window.show();
       //window.setResizable(false);
           
       addFunctionality();
       
    }
		
	private void initComponents() {
			
			//Date column
	       TableColumn<LogfileEntry, String> dateColumn = createColumn("Date", "date", 150);
	       
	       //SessionID column
	       TableColumn<LogfileEntry, String> sessionIdColumn = createColumn("SessionID", "sessionId", 150);
	       
	       //AppName column
	       TableColumn<LogfileEntry, String> appNameColumn = createColumn("App Name", "appName", 100);
	       
	       //Severity column
	       TableColumn<LogfileEntry, String> severityColumn = createColumn("Severity", "severity", 100);
	       
	       //Text column
	       TableColumn<LogfileEntry, String> textColumn = createColumn("Text", "text", 200);
	       
	       //Context column
	       TableColumn<LogfileEntry, String> contextColumn = createColumn("Context", "context", 200);
	                   
	       loadFileBtn = new Button("Load file");
	       saveBtn = new Button("Save file");
	       
	       
	       filterByLbl = new Label("Filter by:");
	       filterCB = new ComboBox<>();
	       filterCB.getItems().addAll("SessionID", "AppName", "Severity");
	       filterCB.getSelectionModel().select(0);
	       
	       filterTF = new TextField();
	       filterTF.setPromptText("Suche...");
	            
	       hBox = new HBox();
	       hBox.setPadding(new Insets(10, 10, 10, 10));
	       hBox.setSpacing(10);
	       hBox.getChildren().addAll(loadFileBtn, filterByLbl, filterCB, filterTF,saveBtn);
	       
	       table = new TableView<>();
	       table.getColumns().addAll(dateColumn, sessionIdColumn, appNameColumn, 
	    		   severityColumn,textColumn, contextColumn);	       
	       table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	       table.setMinHeight(500);
	       table.prefHeightProperty().bind(window.heightProperty());
	       
	       vBox = new VBox();
	       vBox.getChildren().addAll(hBox,table);
	       
	       
	       scene = new Scene(vBox); 
	       
	}
	
	/**
	 * * Die Entities werden mit dieser Methode in die ObservableList geladen
	 * Der Aufruf dieser Methode erfolgt beim Klicken auf den Load File Button
	 * @return
	 */
	private ObservableList<LogfileEntry> getEntries() {
		
		observableEntities = FXCollections.observableArrayList();
		
		/*Dieses Attribut speichert die Fertigen Entities um sie am Ende
		*in die ObservableList reinzuschieben
		*/
		ArrayList<LogfileEntry> parsedEntries = new ArrayList<>();
		
		ArrayList<String> rawEntities = new ArrayList<>();
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logfiles (*.log)", "*.log"));
		
		File logfile = fc.showOpenDialog(window);
		
		if(logfile != null) {
			LogfileReader lr = new LogfileReader();
			
			try {
				lr.loadFile(logfile);
				lr.readFile();
			} catch (IOException e) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText(null);
				alert.setContentText("Fehler beim Laden der Datei!");
				alert.showAndWait();
				
			}
			
			rawEntities = lr.getRawEntryStrings();
			
			/*Es wird versucht das File zu parsen. Es kann eine StringIndexOutOfBoundsException
			*geworfen werden In diesem Fall wird der Benutzer gewarnt, dass etwas mit dem File
			*nicht in Ordnung ist.
			*/
			try {
				LogfileParser lp = new LogfileParser();
				parsedEntries = lp.parseFile(rawEntities);
			} catch (Exception exc) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Logfileformat Fehlerhaft");
				alert.setHeaderText(null);
				alert.setContentText("Das ausgewählte Logfile hat nicht das korrekte Format!");
				alert.showAndWait();
			  
			}
						
			parsedEntries.forEach(entity -> observableEntities.add(entity));
		}
			
		return observableEntities;
	}
	
	private void initFiltering() {
		
		filteredData = new FilteredList<>(observableEntities, p -> true);
		
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
              
            	String filterValue = "";
            	
            	//Hier wird der Filter umgestellt
            	switch(filterCB.getValue()) {
            		case "SessionID": filterValue = entity.getSessionId(); break;
            		case "AppName": filterValue = entity.getAppName(); break;
            		case "Severity": filterValue = entity.getSeverity(); break;
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
		
		SortedList<LogfileEntry> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);
	}
	
	private void addFunctionality() {
		
		/**
		 * Beim Klick werden die Logfileentries in die Tabelle geladen und das
		 * Filtern dieser Elemente wird initialisiert
		 * */
		loadFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				table.setItems(getEntries());				
				initFiltering();
									
			}
		});
		
		/**
		 * Save Button öffnet das Fenster zum speichern des Logfiles.
		 * Es können nur .log Formate gespeichert werden, so dass der
		 * Benutzer nur den Namen eingeben muss.
		 * 
		 * 
		 * */
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				saveFile();
				
			}
		});
	}
	
	private void saveFile() {
		
		ArrayList<String> selectedEntities = new ArrayList<>();
		
		LogfileParser lp = new LogfileParser();
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("LOG", "*.log"));
		File destination = fc.showSaveDialog(window);
		
		table.getItems().forEach(entity -> selectedEntities.add(lp.parseBack(entity)));
	
		if(destination != null && !selectedEntities.isEmpty()) {
			try {
				LogfileWriter.writeBackToFile(selectedEntities, destination);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Erfolgreich gespeichert");
				alert.setHeaderText(null);
				alert.setContentText("Es wurde eine Logdatei mit den "
						+ "modifizierten Ergebnissen erzeugt.");
				alert.showAndWait();
				
			} catch (FileNotFoundException e) {						
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Hilfsmethode um die Table Columns zu initialisieren
	 * */
	private TableColumn<LogfileEntry, String> createColumn(String colName, 
			String entityAttribute, double minWidth) {
		 TableColumn<LogfileEntry, String> tempColumn = new TableColumn<>(colName);
	     tempColumn.setMinWidth(minWidth); 
	     tempColumn.setCellValueFactory(new PropertyValueFactory<>(entityAttribute));
	     return tempColumn;
	}
	
	
} 
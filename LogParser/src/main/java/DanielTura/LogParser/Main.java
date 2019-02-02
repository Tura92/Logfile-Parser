package DanielTura.LogParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logcontrolOLD.LogfileParser;
import logcontrolOLD.LogfileReader;
import logcontrolOLD.LogfileWriter;
import logdata.LogfileEntity;
 
public class Main extends Application {
	
	//Hauptfenster
	Stage window;
	
	//Tabelle zum Anzeigen des Logfiles
	TableView<LogfileEntity> table;
	
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
	ObservableList<LogfileEntity> observableEntities;
	
	//Die Liste
	FilteredList<LogfileEntity> filteredData;
	
	
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
       window.setResizable(false);
       
       
       addFunctionality();
       
    }
	
	
	@SuppressWarnings("unchecked")
	public void initComponents() {
			
			//Date column
	       TableColumn<LogfileEntity, String> dateColumn = createColumn("Date", "date", 150);
	       
	       //SessionID column
	       TableColumn<LogfileEntity, String> sessionIdColumn = createColumn("SessionID", "sessionId", 150);
	       
	       //AppName column
	       TableColumn<LogfileEntity, String> appNameColumn = createColumn("App Name", "appName", 100);
	       
	       //Severity column
	       TableColumn<LogfileEntity, String> severityColumn = createColumn("Severity", "severity", 100);
	       
	       //Text column
	       TableColumn<LogfileEntity, String> textColumn = createColumn("Text", "text", 200);
	       
	       //Context column
	       TableColumn<LogfileEntity, String> contextColumn = createColumn("Context", "context", 200);
	       
	       table = new TableView<>();
	            
	       loadFileBtn = new Button("Load file");
	       saveBtn = new Button("Save file");
	       
	       filterByLbl = new Label("Filter by:");
	       filterCB = new ComboBox<>();
	       filterCB.getItems().addAll("SessionID", "AppName", "Severity");
	       filterCB.getSelectionModel().select(0);
	       
	       filterTF = new TextField();
	            
	       hBox = new HBox();
	       hBox.setPadding(new Insets(10, 10, 10, 10));
	       hBox.setSpacing(10);
	       hBox.getChildren().addAll(loadFileBtn, filterByLbl, filterCB, filterTF,saveBtn);
	             
	       table.getColumns().addAll(dateColumn, sessionIdColumn, appNameColumn, severityColumn,textColumn, contextColumn);
	       	       
	       vBox = new VBox();
	       vBox.getChildren().addAll(hBox,table);
	       scene = new Scene(vBox);
	}
		
	public ObservableList<LogfileEntity> getEntities() {
		
		observableEntities = FXCollections.observableArrayList();
		
		/*Dieses Attribut speichert die Fertigen Entities um sie am Ende
		*in die ObservableList reinzuschieben
		*/
		ArrayList<LogfileEntity> finalEntities = new ArrayList<>();
		
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
			
			rawEntities = lr.getRawEntityStrings();
			
			/*Es wird versucht das File zu parsen. Es kann eine StringIndexOutOfBoundsException
			*geworfen werden falls das Logfileformat nicht korrekt ist. In diesem Fall wird
			*der Benutzer gewarnt.
			*/
			try {
				finalEntities = LogfileParser.parseData(rawEntities);
			} catch (StringIndexOutOfBoundsException exc) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Logfileformat Fehlerhaft");
				alert.setHeaderText(null);
				alert.setContentText("Das ausgewählte Logfile hat nicht das korrekte Format!");
				alert.showAndWait();
			  
			}
			
			
			finalEntities.forEach(entity -> observableEntities.add(entity));
		}
		
		
		
		return observableEntities;
	}
	
	public void initFiltering() {
		
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
                    return true; // Filter matches Textfield Value.
                } 
                return false; // Does not match.
            	       
            });
        });
		
		SortedList<LogfileEntity> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);
	}
	
	public void addFunctionality() {
		loadFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				table.setItems(getEntities());				
				initFiltering();
									
			}
		});
		
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				ArrayList<String> selectedEntities = new ArrayList<>();
				table.getItems().forEach(entity -> {
					selectedEntities.add(entity.toString());
				});
				
				FileChooser fc = new FileChooser();
				fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("LOG", "*.log"));
				File destination = fc.showSaveDialog(window);

				if(destination != null && !selectedEntities.isEmpty()) {
					try {
						LogfileWriter.writeBackToFile(selectedEntities, destination);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Erfolgreich gespeichert");
						alert.setHeaderText(null);
						alert.setContentText("Es wurde eine Logdatei mit den modifizierten Ergebnissen erzeugt.");
						alert.showAndWait();
						
					} catch (FileNotFoundException e) {						
						e.printStackTrace();
					}
				}
				
				
			}
		});
	}
	
	private TableColumn<LogfileEntity, String> createColumn(String colName, String entityAttribute, double minWidth) {
		 TableColumn<LogfileEntity, String> tempColumn = new TableColumn<>(colName);
	     tempColumn.setMinWidth(minWidth); 
	     tempColumn.setCellValueFactory(new PropertyValueFactory<>(entityAttribute));
	     return tempColumn;
	}
	
	
} 
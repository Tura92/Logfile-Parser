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
import logcontrol.LogfileParser;
import logcontrol.LogfileReader;
import logcontrol.LogfileWriter;
import logdata.LogfileEntity;
 
public class Main extends Application {
   
	Stage window;
	TableView<LogfileEntity> table;
	Button loadFileBtn, saveBtn;
	Label filterByLbl;
	ComboBox<String> filterCB;
	TextField filterTF;
	
	HBox hBox;
	VBox vBox;
	Scene scene;
	
	ObservableList<LogfileEntity> observableEntities;
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
	       
	       table = new TableView<>();
	       
	       table.getColumns().addAll(dateColumn, sessionIdColumn, appNameColumn, severityColumn,textColumn, contextColumn);
	       
	       vBox = new VBox();
	       vBox.getChildren().addAll(table, hBox);
	       scene = new Scene(vBox);
	}
		
	public ObservableList<LogfileEntity> getEntities() {
		
		observableEntities = FXCollections.observableArrayList();
		
		ArrayList<LogfileEntity> finalEntities = new ArrayList<>();
		ArrayList<String> rawEntities = new ArrayList<>();
		
		LogfileParser lp = new LogfileParser();
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logfiles (*.log)", "*.log"));
		
		File logfile = fc.showOpenDialog(window);
		
		if(logfile != null) {
			LogfileReader lr = new LogfileReader(logfile);
			
			try {
				
				lr.loadFile();
				lr.readFile();
			} catch (IOException e) {
				
			}
			
			rawEntities = lr.getRawEntityStrings();	
			finalEntities = LogfileParser.parseData(rawEntities);
			finalEntities.forEach(entity -> observableEntities.add(entity));
		}
		
		
		
		return observableEntities;
	}
	
	public void initFiltering() {
		
		filteredData = new FilteredList<>(observableEntities, p -> true);
		
		filterTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
              
            	String filterValue = "";
            	
            	switch(filterCB.getValue()) {
            		case "SessionID": filterValue = entity.getSessionId(); break;
            		case "AppName": filterValue = entity.getAppName(); break;
            		case "Severity": filterValue = entity.getSeverity(); break;
            	}
            	
            	// If filter text is empty, display all Logfileentities.
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

				if(destination != null) {
					try {
						LogfileWriter.writeBackToFile(selectedEntities, destination);
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
package logview;

import java.util.Date;

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
import javafx.stage.Stage;
import logdata.LogfileEntry;
import logdata.Severity;
import tablecontrol.ListManager;

public class MainWindow {
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
		
		HBox topBar;
		VBox vBox;
		Scene scene;
				
		ListManager listManager;
				
		public MainWindow(Stage primaryStage) {
			
			window = primaryStage;
			
			listManager = new ListManager();
			
			initComponents();	
		
			addFunctionality();
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		
		private void initComponents() {
			
			//Date column
		       TableColumn<LogfileEntry, Date> dateColumn = createDateColumn("Date", "date", 200);
		       
		       //SessionID column
		       TableColumn<LogfileEntry, String> sessionIdColumn = createStringColumn("SessionID", "sessionId", 150);
		       
		       //AppName column
		       TableColumn<LogfileEntry, String> appNameColumn = createStringColumn("App Name", "appName", 100);
		       
		       //Severity column
		       TableColumn<LogfileEntry, Severity> severityColumn = createSeverityColumn("Severity", "severity", 100);
		       
		       //Text column
		       TableColumn<LogfileEntry, String> textColumn = createStringColumn("Text", "text", 200);
		       
		       //Context column
		       TableColumn<LogfileEntry, String> contextColumn = createStringColumn("Context", "context", 200);
		                   
		       loadFileBtn = new Button("Load file");
		       saveBtn = new Button("Save file");
		       
		       filterByLbl = new Label("Filter by:");
		       filterCB = new ComboBox<>();
		       filterCB.getItems().addAll("SessionID", "AppName", "Severity");
		       filterCB.getSelectionModel().select(0);
		       
		       filterTF = new TextField();
		       filterTF.setPromptText("Suche...");
		            
		       topBar = new HBox();
		       topBar.setPadding(new Insets(10, 10, 10, 10));
		       topBar.setSpacing(10);
		       topBar.getChildren().addAll(loadFileBtn, filterByLbl, filterCB, filterTF,saveBtn);
		       
		       table = new TableView<>();
		       table.getColumns().addAll(dateColumn, sessionIdColumn, appNameColumn, 
		    		   severityColumn,textColumn, contextColumn);	       
		       table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		       table.setMinHeight(500);
		       table.prefHeightProperty().bind(window.heightProperty());
		       
		       vBox = new VBox();
		       vBox.getChildren().addAll(topBar,table);
		       
		       
		       scene = new Scene(vBox); 
	       
		}
		
		private void addFunctionality() {
			
			/**
			 * Beim Klick werden die Logfileentries in die Tabelle geladen und das
			 * Filtern dieser Elemente wird initialisiert
			 * */
			loadFileBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					table.setItems(listManager.getEntries(window));				
					listManager.initFiltering(table, filterTF, filterCB);
										
				}
			});
			
			/**
			 * Save Button öffnet das Fenster zum speichern des Logfiles.
			 * Es können nur .log Formate gespeichert werden, so dass der
			 * Benutzer nur den Namen eingeben muss.
			 * */
			saveBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					listManager.saveFile(window, table);
					
				}
			});
		}

		/**
		 * Hilfsmethoden um die Table Columns zu initialisieren
		 * */
		private TableColumn<LogfileEntry, String> createStringColumn(String colName, 
				String entityAttribute, double minWidth) {
			 TableColumn<LogfileEntry, String> tempColumn = new TableColumn<>(colName);
		     tempColumn.setMinWidth(minWidth); 
		     tempColumn.setCellValueFactory(new PropertyValueFactory<>(entityAttribute));
		     return tempColumn;
		}
		
		private TableColumn<LogfileEntry, Date> createDateColumn(String colName, 
				String entityAttribute, double minWidth) {
			 TableColumn<LogfileEntry, Date> tempColumn = new TableColumn<>(colName);
		     tempColumn.setMinWidth(minWidth); 
		     tempColumn.setCellValueFactory(new PropertyValueFactory<>(entityAttribute));
		     return tempColumn;
		}

		private TableColumn<LogfileEntry, Severity> createSeverityColumn(String colName, 
				String entityAttribute, double minWidth) {
			 TableColumn<LogfileEntry, Severity> tempColumn = new TableColumn<>(colName);
		     tempColumn.setMinWidth(minWidth); 
		     tempColumn.setCellValueFactory(new PropertyValueFactory<>(entityAttribute));
		     return tempColumn;
		}
}

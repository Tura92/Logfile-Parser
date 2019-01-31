package DanielTura.LogParser;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logdata.LogfileParser;
 
public class Main extends Application {
   
	Stage window;
	
	
	
	
    public static void main(String[] args) {
        launch(args);
    }
 
    
	@Override
    public void start(Stage primaryStage) {
       window = primaryStage;
       window.setTitle("Logfile Parser");
       
       VBox vBox = new VBox();
       vBox.getChildren().addAll();
       
       LogfileParser lp = new LogfileParser();
       lp.parseData();
       
       
    }
 
    
    
} 
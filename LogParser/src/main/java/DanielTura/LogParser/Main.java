package DanielTura.LogParser;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;
import logview.MainWindow;
 
public class Main extends Application {
		
    public static void main(String[] args) {
        launch(args);
    }
 
	@Override
    public void start(Stage primaryStage) throws FileNotFoundException {
      
		new MainWindow(primaryStage);
		      
    }
	
} 
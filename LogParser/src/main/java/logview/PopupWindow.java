package logview;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PopupWindow {
	
	public static void error(String head, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(head);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void info(String head, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(head);
		alert.setContentText(content);
		alert.showAndWait();
	}
}

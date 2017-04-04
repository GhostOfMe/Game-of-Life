package gameOfLife;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutWindow {

	private static String aboutText = 
			
			"The Game of Life, also known simply as Life, is a cellular automaton "
			+ "devised by the British mathematician John Horton Conway in 1970."
			+ "\n\n"
			+ "The \"game\" is a zero-player game, meaning that its evolution is "
			+ "determined by its initial state, requiring no further input. One "
			+ "interacts with the Game of Life by creating an initial configuration "
			+ "and observing how it evolves, or, for advanced \"players\", by creating "
			+ "patterns with particular properties.";
	
	public static void Display(ObservableList<String> styleSheets) {
		
		Stage window = new Stage();
	
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("About \"Game of Life\"");
		window.setMaxHeight(200);
		window.setMinHeight(200);
		window.setMaxWidth(480);
		window.setMinWidth(480);
		
		Text text = new Text(aboutText);
		text.setWrappingWidth(400);
		
		Button okButton = new Button("Ok");
		okButton.setOnAction(e -> window.close());
		
		HBox buttonMenu = new HBox(10);
		buttonMenu.getChildren().addAll(okButton);
		buttonMenu.setAlignment(Pos.CENTER);
		
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(text);
		mainPane.setBottom(buttonMenu);
		
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().addAll(styleSheets);
		window.setScene(scene);
		window.showAndWait();
	}
}
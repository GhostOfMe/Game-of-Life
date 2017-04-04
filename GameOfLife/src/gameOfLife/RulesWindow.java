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

public class RulesWindow {

	private static String rulesText = 
			
			"The universe of the Game of Life is an infinite two-dimensional "
			+ "orthogonal grid of square cells, each of which is in one of two "
			+ "possible states, alive or dead, or \"populated\" or \"unpopulated\". "
			+ "Every cell interacts with its eight neighbours, which are the cells "
			+ "that are horizontally, vertically, or diagonally adjacent. At each "
			+ "step in time, the following transitions occur:"
			+ "\n\n"
			+ "\t 1) Any live cell with fewer than two live neighbours dies, as if "
			+ "caused by underpopulation.\n"
			+ "\t 2) Any live cell with two or three live neighbours lives on to "
			+ "the next generation.\n"
			+ "\t 3) Any live cell with more than three live neighbours dies, as "
			+ "if by overpopulation.\n"
			+ "\t 4) Any dead cell with exactly three live neighbours becomes a "
			+ "live cell, as if by reproduction. "
			+ "\n\n"
			+ "The initial pattern constitutes the seed of the system. The first "
			+ "generation is created by applying the above rules simultaneously "
			+ "to every cell in the seed—births and deaths occur simultaneously, "
			+ "and the discrete moment at which this happens is sometimes called "
			+ "a tick (in other words, each generation is a pure function of the "
			+ "preceding one). The rules continue to be applied repeatedly to "
			+ "create further generations.";
	
	public static void Display(ObservableList<String> styleSheets) {
		
		Stage window = new Stage();
	
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Rules");
		window.setMaxHeight(320);
		window.setMinHeight(320);
		window.setMaxWidth(620);
		window.setMinWidth(620);
		
		Text text = new Text(rulesText);
		text.setWrappingWidth(520);
		
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

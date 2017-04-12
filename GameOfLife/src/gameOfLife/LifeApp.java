package gameOfLife;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LifeApp extends Application{

	private static final int TILE_SIZE = 20;
	private static final int W = 640;
	private static final int H = 480;
	
	private static final int X_TILES = W / TILE_SIZE;
	private static final int Y_TILES = H / TILE_SIZE;
	
	private Tile[][] grid = new Tile[X_TILES][Y_TILES];
	
	Timeline timeline = new Timeline();
	private static final int DELAY = 350;
	
	private Parent createContent() {
		
		Pane root = new Pane();
		root.setPrefSize(W, H);
		
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = new Tile(x, y, Math.random() < 0.2);

				grid[x][y] = tile;
				root.getChildren().add(tile);
			}
		}
		updateNeighbours();
	
		return root;
	}
	
	private List<Tile> getNeighbours(Tile tile){
		
		List<Tile> neighbours = new ArrayList();
		
		int[] points = new int[] {
			
				-1, -1,
				-1,  0,
				-1,  1,
				 0, -1,
				 0,  1,
				 1, -1,
				 1,  0,
				 1,  1
		
		};
		
		for (int i = 0; i < points.length; i++) {
			int dx = points[i];
			int dy = points[++i];
			
			int newX = tile.x + dx;
			int newY = tile.y + dy;
			
			if (newX >=0 && newX < X_TILES
					&& newY >= 0 && newY < Y_TILES) {
				if(grid[newX][newY].isAlive) {
					neighbours.add(grid[newX][newY]);
				}
			}
		}
		
		return neighbours;
	}
	
	
	private class Tile extends StackPane {
		
		private int x;
		private int y;
		private int neighbours = 0;
	
		private boolean isAlive;
		
		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		
		public Tile(int x, int y, boolean isAlive) {
			
			this.x = x;
			this.y = y;
			this.isAlive = isAlive;
			
			if (isAlive)
				border.getStyleClass().add("rect-alive");
			else
				border.getStyleClass().add("rect-empty");
			
			getChildren().add(border);
			
			setTranslateX(x * TILE_SIZE);
			setTranslateY(y * TILE_SIZE);
		
			setOnMouseClicked(e -> {
				switchState();
				updateNeighbours();
			});
		}
	
		public void setState(boolean isAlive) {
			
			if(isAlive) {
				this.isAlive = true;
				border.getStyleClass().clear();
				border.getStyleClass().add("rect-alive");
			}else {
				this.isAlive = false;
				border.getStyleClass().clear();
				border.getStyleClass().add("rect-empty");
			}
		}
		
		public void switchState() {
			
			if(this.isAlive) {
				this.isAlive = false;
				border.getStyleClass().clear();
				border.getStyleClass().add("rect-empty");
			}else {
				this.isAlive = true;
				border.getStyleClass().clear();
				border.getStyleClass().add("rect-alive");
			}
		}
	}
		
	public Tile[][] getGrid() {
		return grid;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = FXMLLoader.load(getClass().getResource("/gameOfLife/res/GUI.fxml"));
		
		Scene scene = new Scene(root);	
		root.setCenter(createContent());
		
		scene.getStylesheets().add("gameOfLife/res/default.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Conway's Game of Life");
		
		//BufferedImage icon = ImageIO.read(ResourceLoader.load("icon.png"));
		//primaryStage.getIcons().add(icon);
		Image icon = new Image("file:src/gameOfLife/res/icon.png");
		if (icon.isError()){
			icon = new Image("file:icon.png");
		}
		System.out.println(icon);
		primaryStage.getIcons().add(icon);



		primaryStage.setResizable(false);
		
		
		
		primaryStage.show();
		
		// Setting timeline poperties
		Controller.timeline = timeline;
		Controller.game = this;
		Controller.scene = scene;
		
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.millis(DELAY), (e) -> Update()));
		timeline.setCycleCount(1000);

	}


	public void onClear() {

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = grid[x][y];
				
				tile.setState(false);
				updateNeighbours();
			}
		}
	}

	private void Update() {
		
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = grid[x][y];
				checkState(tile);
				}
			}
		updateNeighbours();
	}
	
	public void updateNeighbours() {
		
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = grid[x][y];
				tile.neighbours =  getNeighbours(tile).size();
			}
		}
	}
		
	private void checkState(Tile tile) {

		if (tile.isAlive) {
			if (tile.neighbours == 2 || tile.neighbours == 3) {
				tile.setState(true);
			}
			
			else
				tile.setState(false);
		
		} else {
			if (tile.neighbours == 3)
				tile.setState(true);
			else
				tile.setState(false);
		
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void randomize() {

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = grid[x][y];
				 
				tile.setState(Math.random() < 0.2);
				updateNeighbours();
			
			}
		}
	}

	public boolean[][] getState() {
		boolean[][]state = new boolean[X_TILES][Y_TILES];
		
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				state[x] [y] = grid[x][y].isAlive;	 
			}
		}
		return state;
	}

	public void loadState(int[][] gliderState) {

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				grid[x][y].setState(gliderState[x][y] > 0 ? true : false);
				}
			}
		
		updateNeighbours();
	}
}

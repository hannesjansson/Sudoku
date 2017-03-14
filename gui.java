package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class gui extends Application {
	private Sudoku game;
	private Button solveButton, clearButton;
	private GridPane grid;

	/**
	 * The entry point for the Java program.
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		game = new Sudoku();
		BorderPane root = new BorderPane();
		grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setStyle("-fx-background-color:#606772; -fx-opacity:1;");
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				TextCell cell = new TextCell();
				if (i / 3 != 1 && j / 3 != 1 || i / 3 == 1 && j / 3 == 1) {
					cell.setStyle("-fx-background-color: #ff9900;");
				}
				cell.setEditable(true);
				cell.setAlignment(Pos.CENTER);
				cell.setMaxSize(30, 30);
				cell.setOnKeyPressed(event -> cell.setText(event.getText()));
				grid.add(cell, i, j);
			}
		}

		HBox buttons = new HBox();
		buttons.setSpacing(5);
		buttons.setPadding(new Insets(10, 10, 20, 10));
		buttons.setStyle("-fx-background-color:#D3D3D3; -fx-opacity:1;");

		clearButton = new Button("Clear");
		clearButton.setAlignment(Pos.CENTER);
		clearButton.setOnAction(e -> clear());

		solveButton = new Button("Solve");
		solveButton.setAlignment(Pos.CENTER);
		solveButton.setOnAction(e -> solve());

		buttons.getChildren().addAll(solveButton, clearButton);

		root.setTop(grid);
		root.setBottom(buttons);

		Scene scene = new Scene(root, 310, 350);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Sudoku");
		stage.show();
	}

	/** Finds the cell that corresponds to the same matrix element. */
	private TextCell findCell(int i, int j) {
		for (Node node : grid.getChildren()) {
			if (i == GridPane.getRowIndex(node)) {
				if (j == GridPane.getColumnIndex(node)) {
					return (TextCell) node;
				}
			}
		}
		return null;
	}

	/**
	 * Reads user input, solves the sudoku and prints the solution.
	 */
	private void solve() {
		game = new Sudoku();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				TextCell cell = findCell(i, j);
				String text = cell.getText();
				if (!text.isEmpty()) {
					if (text.matches("[1-9]+")) {
						int nbr = Integer.parseInt(text);
						game.setNbr(i, j, nbr);
					} else {
						Dialogs.alert("Wrong input", null, "You can only input integers from 1 to 9.");
						return;
					}
				}
			}
		}
		if (game.solve()) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					TextCell cell = findCell(i, j);
					cell.setText(Integer.toString(game.getNbr(i, j)));
				}
			}
		} else {
			Dialogs.alert("Fail", null, "Could not solve the sudoku.");
		}
	}

	/**
	 * Resets the sudoku and clears the window.
	 */
	private void clear() {
		for (Node node : grid.getChildren()) {
			TextCell cell = (TextCell) node;
			cell.clear();
		}
		game = new Sudoku();
	}
}
package game;

public class Sudoku {
	private int[][] board;

	/**
	 * Create an empty sudoku board, which is defined as a filled board with -1.
	 */
	public Sudoku() {
		board = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = -1;
			}
		}
	}

	/**
	 * Return the value in a specific cell.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @param j
	 *            the column of the sudoku
	 * @return The number in the cell.
	 */
	public int getNbr(int i, int j) {
		return board[i][j];
	}

	/**
	 * Set the value in a specific cell.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @param j
	 *            the column of the sudoku
	 * @param value
	 *            the value to put in cell
	 */
	public void setNbr(int i, int j, int value) {
		board[i][j] = value;
	}

	/**
	 * Check if a cell is empty.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @param j
	 *            the col of the sudoku
	 * @return True if the cell is empty. False otherwise.
	 */
	public boolean isEmpty(int i, int j) {
		return board[i][j] == -1;
	}

	/**
	 * Solves the sudoku.
	 * 
	 * @return True if the sudoku is solvable. False otherwise.
	 */
	public boolean solve() {
		return solve(0, 0);
	}

	/**
	 * Checks each cell recursive and solves the sudoku.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @param j
	 *            the column of the sudoku
	 * @return True if the sudoku has been solved.
	 */
	private boolean solve(int i, int j) {
		if (i > 8) { // om vi har nått slutet
			return true;
		} else if (j > 8) { // om vi är i slutet av en rad
			return solve(i + 1, 0);
		} else {
			if (isEmpty(i, j)) { // om rutan är tom
				for (int value = 1; value <= 9; value++) {
					if (checkRow(i, value) && checkCol(j, value) && checkBox(i, j, value)) {
						setNbr(i, j, value);
						if (solve(i, j + 1)) {
							return true;
						} else {
							setNbr(i, j, -1);
						}
					}
				}
				return false;
			} else {
				int value = getNbr(i, j);
				setNbr(i, j, -1);
				if (checkRow(i, value) && checkCol(j, value) && checkBox(i, j, value)) {
					setNbr(i, j, value);
					if (solve(i, j + 1)) {
						return true;
					} else {
						return false;
					}
				} else {
					setNbr(i, j, value);
					return false;
				}
			}
		}
	}

	/**
	 * Check if a row contains a value.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @return True if the row contains the value. False otherwise.
	 */
	private boolean checkRow(int i, int value) {
		for (int j = 0; j < 9; j++) {
			if (getNbr(i, j) == value) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the a column contains value.
	 * 
	 * @param j
	 *            the column of the sudoku
	 * @return True if the column contains the value. False otherwise.
	 */
	private boolean checkCol(int j, int value) {
		for (int i = 0; i < 9; i++) {
			if (getNbr(i, j) == value) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the 3x3 box that the cell is in contains value.
	 * 
	 * @param i
	 *            the row of the sudoku
	 * @param j
	 *            the column of the sudoku
	 * @param value
	 *            the number in the cell
	 * @return True if the box contains the value. False otherwise.
	 */
	private boolean checkBox(int i, int j, int value) {
		int row = i / 3;
		int col = j / 3;
		for (int k = 0; k < 3; k++) {
			for (int t = 0; t < 3; t++) {
				if (value == getNbr(k + 3 * row, t + 3 * col)) {
					return false;
				}
			}
		}
		return true;
	}
}
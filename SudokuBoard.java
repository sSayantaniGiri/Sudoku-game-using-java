public class SudokuBoard {
	private int[][] board;
	private int[][] solution;
	private int size = 9;

	public SudokuBoard(int[][] board, int[][] solution) {
		this.board = board;
		this.solution = solution;
	}

	public int getCell(int row, int col) {
		return board[row][col];
	}

	public int getSolutionCell(int row, int col) {
		return solution[row][col];
	}

	public void setCell(int row, int col, int value) {
		board[row][col] = value;
	}

	public boolean isValidMove(int row, int col, int value) {
		// Check row, column and 3x3 box for validity
		for (int i = 0; i < size; i++) {
			if (board[row][i] == value || board[i][col] == value) {
				return false;
			}
		}
		int boxRowStart = (row / 3) * 3;
		int boxColStart = (col / 3) * 3;
		for (int r = boxRowStart; r < boxRowStart + 3; r++) {
			for (int c = boxColStart; c < boxColStart + 3; c++) {
				if (board[r][c] == value) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isComplete() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isSolved() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] != solution[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}

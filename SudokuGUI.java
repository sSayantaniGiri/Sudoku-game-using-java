import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SudokuGUI extends JFrame {
	private SudokuBoard board;
	private JTextField[][] cells;
	private final int SIZE = 9;
	private JLabel timerLabel;
	private Timer timer;
	private int elapsedTime = 0;

	public SudokuGUI(SudokuBoard board) {
		this.board = board;
		cells = new JTextField[SIZE][SIZE];
		initUI();
		startTimer();
	}

	private void initUI() {
		setTitle("Sudoku");
		setSize(600, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(SIZE, SIZE));
		Font font = new Font("Arial", Font.BOLD, 20);

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				cells[i][j] = new JTextField();
				cells[i][j].setHorizontalAlignment(JTextField.CENTER);
				cells[i][j].setFont(font);
				if (board.getCell(i, j) != 0) {
					cells[i][j].setText(String.valueOf(board.getCell(i, j)));
					cells[i][j].setEditable(false);
				} else {
					cells[i][j].setText("");
				}
				panel.add(cells[i][j]);
			}
		}

		JButton checkButton = new JButton("Check Solution");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.isComplete() && board.isSolved()) {
					JOptionPane.showMessageDialog(null, "Congratulations! You've solved the puzzle!");
				} else {
					JOptionPane.showMessageDialog(null, "The solution is incorrect. Please try again.");
				}
			}
		});

		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < SIZE; i++) {
					for (int j = 0; j < SIZE; j++) {
						if (board.getCell(i, j) == 0) {
							cells[i][j].setText("");
						}
					}
				}
				elapsedTime = 0;
				timerLabel.setText("Time: 0s");
			}
		});

		JButton hintButton = new JButton("Hint");
		hintButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				giveHint();
			}
		});

		timerLabel = new JLabel("Time: 0s");
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));

		JPanel controlPanel = new JPanel();
		controlPanel.add(checkButton);
		controlPanel.add(restartButton);
		controlPanel.add(hintButton);
		controlPanel.add(timerLabel);

		add(panel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}

	private void startTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				elapsedTime++;
				timerLabel.setText("Time: " + elapsedTime + "s");
			}
		}, 1000, 1000);
	}

	private void giveHint() {
		Random rand = new Random();
		int row, col;

		do {
			row = rand.nextInt(SIZE);
			col = rand.nextInt(SIZE);
		} while (board.getCell(row, col) != 0);

		int correctValue = board.getSolutionCell(row, col);
		cells[row][col].setText(String.valueOf(correctValue));
		board.setCell(row, col, correctValue);
	}

	public static void main(String[] args) {
		// Sample board and solution
		int[][] board = {
				{5, 3, 0, 0, 7, 0, 0, 0, 0},
				{6, 0, 0, 1, 9, 5, 0, 0, 0},
				{0, 9, 8, 0, 0, 0, 0, 6, 0},
				{8, 0, 0, 0, 6, 0, 0, 0, 3},
				{4, 0, 0, 8, 0, 3, 0, 0, 1},
				{7, 0, 0, 0, 2, 0, 0, 0, 6},
				{0, 6, 0, 0, 0, 0, 2, 8, 0},
				{0, 0, 0, 4, 1, 9, 0, 0, 5},
				{0, 0, 0, 0, 8, 0, 0, 7, 9}
		};

		int[][] solution = {
				{5, 3, 4, 6, 7, 8, 9, 1, 2},
				{6, 7, 2, 1, 9, 5, 3, 4, 8},
				{1, 9, 8, 3, 4, 2, 5, 6, 7},
				{8, 5, 9, 7, 6, 1, 4, 2, 3},
				{4, 2, 6, 8, 5, 3, 7, 9, 1},
				{7, 1, 3, 9, 2, 4, 8, 5, 6},
				{9, 6, 1, 5, 3, 7, 2, 8, 4},
				{2, 8, 7, 4, 1, 9, 6, 3, 5},
				{3, 4, 5, 2, 8, 6, 1, 7, 9}
		};

		SudokuBoard sudokuBoard = new SudokuBoard(board, solution);
		SudokuGUI gui = new SudokuGUI(sudokuBoard);
		gui.setVisible(true);
	}
}

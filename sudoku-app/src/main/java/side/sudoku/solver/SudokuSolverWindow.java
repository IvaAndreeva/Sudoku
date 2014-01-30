package side.sudoku.solver;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import side.sudoku.exceptions.SudokuException;
import side.sudoku.game.Sudoku;
import side.sudoku.game.SudokuSimpleImpl;

public class SudokuSolverWindow {
	private Sudoku sudoku;
	private Display display;
	private Shell shell;
	private SudokuGrid sudokuGrid;

	public SudokuSolverWindow(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	public void solve() {
		if (sudoku != null) {
			display = new Display();
			shell = new Shell(display);

			GridLayout layout = new GridLayout();
			shell.setLayout(layout);
			shell.setSize(sudoku.getWholeBoardSize().verticalCount * 40 + 200,
					sudoku.getWholeBoardSize().horizontalCount * 40 + 200);
			shell.setLocation(300, 300);
			Label label = new Label(shell, SWT.NONE);
			label.setText("Solve it and click check. You have 2 tries per cell.");

			sudokuGrid = new SudokuGrid(shell, SWT.NONE, sudoku);
			sudokuGrid.setEnabled(true);

			final Button checkButton = initializeCheckButton();

			initializeGenerateNewButton(checkButton);

			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.dispose();
		}
	}

	private Button initializeCheckButton() {
		final Button checkButton = new Button(shell, SWT.PUSH);
		checkButton.setText("Check answers");
		checkButton.setEnabled(true);

		final Label keepTrying = new Label(shell, SWT.NONE);
		keepTrying
				.setText("Keep guessing, you have either incorrect guesses or empty cells");
		keepTrying.setVisible(false);

		checkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sudoku != null) {
					checkButton.setText("Checking...");
					checkButton.setEnabled(false);
					checkButton.pack();
					drawButton(checkButton, "Checking...", false);

					guessValues(sudokuGrid.getChangedCells());
					sudokuGrid.resetChanged();

					new Thread(new Runnable() {
						public void run() {

							shell.getDisplay().asyncExec(new Runnable() {
								public void run() {
									if (sudoku.gameOver) {
										drawButton(checkButton, "GAME OVER",
												false);
										keepTrying.setVisible(false);
										return;
									}
									if (sudoku.gameFinished) {
										drawButton(checkButton, "CONGRATS",
												false);
										keepTrying.setVisible(false);
										return;
									}
									keepTrying.setVisible(true);
									drawButton(checkButton, "Check answers",
											true);
								}
							});
						}
					}).start();
				}
			}
		});
		return checkButton;
	}

	private Button initializeGenerateNewButton(final Button checkButton) {
		Button generateNewButton = new Button(shell, SWT.PUSH);
		generateNewButton.setText("Generate new");
		generateNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Button button = (Button) e.getSource();
				drawButton(button, "Generating...", false);

				new Thread(new Runnable() {
					public void run() {
						sudoku = new SudokuSimpleImpl().create(
								sudoku.getSize(), 1);
						shell.getDisplay().asyncExec(new Runnable() {
							public void run() {
								sudokuGrid.createContents(sudoku);
								sudokuGrid.layout();
								drawButton(button, "Generate new", true);
								drawButton(checkButton, "Check answers", true);
							}
						});
					}
				}).start();
			}
		});
		return generateNewButton;
	}

	private void guessValues(List<SudokuCellInfo> changedCells) {
		for (SudokuCellInfo text : changedCells) {
			try {
				sudoku.guessValueOnLocation(text.value, text.x, text.y);
			} catch (SudokuException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawButton(Button button, String text, boolean canPlay) {
		button.setText(text);
		button.pack();
		button.setEnabled(canPlay);
		sudokuGrid.setEnabled(canPlay);
	}
}

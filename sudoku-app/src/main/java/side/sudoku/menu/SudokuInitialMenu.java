package side.sudoku.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import side.sudoku.entities.SudokuSize;
import side.sudoku.game.Sudoku;
import side.sudoku.game.SudokuSimpleImpl;
import side.sudoku.properties.SudokuAvailableDifficulties;
import side.sudoku.properties.SudokuAvailableSizes;

public class SudokuInitialMenu {
	private Display display;
	private Shell shell;
	private Sudoku sudoku = null;
	private SudokuSize pickedSize = null;
	private int pickedDifficulty = 1;

	public Sudoku initSudoku() {
		display = new Display();
		shell = new Shell(display);

		shell.setLayout(new RowLayout());
		shell.setSize(700, 100);
		shell.setLocation(300, 300);
		Label label = new Label(shell, SWT.BORDER);
		label.setText("Choose sudoku size from available:");
		label.pack();

		Combo difficultyMenu = initializeDifficultyMenu();

		initializeMenu(difficultyMenu);

		initializeGenerateButton();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		return sudoku;
	}

	private Combo initializeMenu(final Combo difficultyMenu) {
		Combo menu = new Combo(shell, SWT.READ_ONLY);
		for (SudokuSize size : SudokuAvailableSizes.sizes) {
			menu.add(size.toString());
		}
		menu.select(0);
		menu.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		menu.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		menu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pickedSize = SudokuAvailableSizes.getSizeByString(((Combo) e
						.getSource()).getText());
				if (pickedSize == SudokuAvailableSizes.getSizeByString("6 x 6")) {
					difficultyMenu.select(0);
					difficultyMenu.setEnabled(false);
				} else {
					difficultyMenu.setEnabled(true);
				}
			}
		});
		menu.pack();
		pickedSize = SudokuAvailableSizes.getSizeByString(menu.getItem(menu
				.getSelectionIndex()));
		return menu;
	}

	private Combo initializeDifficultyMenu() {
		Combo menu = new Combo(shell, SWT.READ_ONLY);
		for (String difficulty : SudokuAvailableDifficulties.getDifficulties()) {
			menu.add(difficulty);
		}
		menu.select(0);
		menu.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		menu.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		menu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pickedDifficulty = SudokuAvailableDifficulties
						.getDifficultyByString(((Combo) e.getSource())
								.getText());
			}
		});
		menu.pack();
		pickedDifficulty = SudokuAvailableDifficulties
				.getDifficultyByString(menu.getItem(menu.getSelectionIndex()));
		return menu;
	}

	private void initializeGenerateButton() {
		final Button generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate");

		generateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sudoku == null) {
					drawButton(generateButton, "Generating...", false);

					new Thread(new Runnable() {
						public void run() {
							sudoku = new SudokuSimpleImpl().create(pickedSize,
									pickedDifficulty);
							asynchShellDispose();
						}
					}).start();
				}
			}
		});
	}

	private void drawButton(Button button, String text, boolean enabled) {
		button.setText(text);
		button.pack();
		button.setEnabled(enabled);
	}

	private void asynchShellDispose() {
		shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				shell.dispose();
			}
		});
	}
}

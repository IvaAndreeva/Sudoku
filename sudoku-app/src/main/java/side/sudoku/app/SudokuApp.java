package side.sudoku.app;

import side.sudoku.game.Sudoku;
import side.sudoku.menu.SudokuInitialMenu;
import side.sudoku.solver.SudokuSolverWindow;

public class SudokuApp {
	public static void main(String[] args) {
		SudokuInitialMenu menu = new SudokuInitialMenu();
		Sudoku sudoku = menu.initSudoku();
		SudokuSolverWindow solver = new SudokuSolverWindow(sudoku);
		solver.solve();
	}
}

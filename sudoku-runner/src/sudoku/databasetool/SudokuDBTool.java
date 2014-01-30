package sudoku.databasetool;

import persistence.classes.DBActions;
import side.sudoku.entities.SudokuSize;
import side.sudoku.game.Sudoku;
import side.sudoku.game.SudokuSimpleImpl;

public class SudokuDBTool {
	public static Sudoku generateSudoku(int verticalBlockNumber,
			int horizontalBlockNumber, int verticalCellNumber,
			int horizontalCellNumber, int difficulty) {
		Sudoku sudoku = new SudokuSimpleImpl().create(verticalBlockNumber,
				horizontalBlockNumber, verticalCellNumber,
				horizontalCellNumber, difficulty);
		DBActions.addSudoku(sudoku);
		return sudoku;
	}

	public static Sudoku generateSudoku(SudokuSize size, int difficulty) {
		Sudoku sudoku = new SudokuSimpleImpl().create(size, difficulty);
		DBActions.addSudoku(sudoku);
		return sudoku;
	}

	public static Sudoku getSudokuFromDB(int id) {
		return DBActions.getSudoku(id);
	}
}

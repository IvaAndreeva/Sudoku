package side.sudoku.subtractors;

import side.sudoku.entities.Board;
import side.sudoku.entities.Cell;
import side.sudoku.entities.Coordinate;
import side.sudoku.entities.EntityRow;
import side.sudoku.solvers.SudokuSolver;
import side.sudoku.tools.SudokuGeneratorTools;

public class SudokuNumberSubtractor {
	public static Board subtractCellsFromBoard(Board board, int difficulty) {
		int numClues = ((Double) Math.floor(board.getWholeSize().horizontalCount
				* board.getWholeSize().verticalCount / 3)).intValue();
		Board workingBoard = board.clone();
		subtractUntilClueNumberCellsAtATime(workingBoard, numClues, 2);
		while (!SudokuSolver.solutionMatchesDifficulty(workingBoard, difficulty)) {
			workingBoard = board.clone();
			subtractUntilClueNumberCellsAtATime(workingBoard, numClues, 2);
		}
		return workingBoard;
	}

	private static void subtractCells(Board board, int cellsToSubtractCount) {
		Cell subtractedCell = null;
		for (int i = 0; i < cellsToSubtractCount; i++) {
			subtractedCell = subtractCell(board);
			if (!SudokuSolver.solutionIsUnique(board)) {
				i--;
				revertSubtractedCell(subtractedCell);
				subtractedCell = null;
			}
		}
	}

	private static Cell subtractCell(Board board) {
		int cellToSubtractVerticalInd = SudokuGeneratorTools
				.getRandomIndexMax(board.getWholeSize().verticalCount);
		int cellToSubtractHorizontalInd = SudokuGeneratorTools
				.getRandomIndexMax(board.getWholeSize().horizontalCount);
		Cell cellToSubtract = board.getCellOnLocation(new Coordinate(
				cellToSubtractHorizontalInd, cellToSubtractVerticalInd));
		cellToSubtract.setCanChangeUpdateValueToShow(true);
		return cellToSubtract;
	}

	private static void revertSubtractedCell(Cell subtractedCell) {
		subtractedCell.setCanChangeUpdateValueToShow(false);
	}

	private static void subtractUntilClueNumberCellsAtATime(Board board,
			int clueNumber, int cellsToSubtractCount) {
		int cluesCount = getCluesCount(board);
		while (cluesCount > clueNumber) {
			subtractCells(board, cellsToSubtractCount);
			cluesCount = getCluesCount(board);
		}
	}

	private static int getCluesCount(Board board) {
		int clueCount = 0;
		for (EntityRow<Cell> row : board.getTablesOfCells()) {
			for (Cell cell : row) {
				if (!cell.isCanChange()) {
					clueCount++;
				}
			}
		}
		return clueCount;
	}

	private void simple(Board board) {
		boolean isHidden = false;
		for (EntityRow<Cell> row : board.getTablesOfCells()) {
			for (Cell cell : row) {
				cell.setCanChange(!isHidden);
				isHidden = !isHidden;
				if (cell.isCanChange()) {
					cell.setValueToShow("");
				}
			}
		}
	}
}
